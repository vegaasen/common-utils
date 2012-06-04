package com.suiveg.utils.http.handlers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple Wildcard-based hostname verifier
 * <p/>
 * Some WebLogic releases have some problems with the interpreting wildcard-certificates.
 * This class solves that problem and verifies the "problematic" wildcard-based certificates
 * <p/>
 * Todo: Replace the deprecated classes with the non-deprecated ones. -- with what then smart-ass?!
 *
 * @author t769765
 * @since 1.16-SNAPSHOT
 */
public class WildcardHostname {

    public static WebLogicHostname webLogicHostname = new WebLogicHostname();
    public static DefaultHostname defaultHostname = new DefaultHostname();

    private static final Logger LOGGER = Logger.getLogger(WildcardHostname.class.getCanonicalName());
    private static final Pattern VALID_HOST_PATTERN = Pattern.compile("\\*\\.[^*]*\\.[^*]*");
    private static final Pattern COMMON_NAME_PATTERN = Pattern.compile("CN=([-.*aA-zZ0-9]*)");
    private static final String CERTIFICATE_TYPE_X_509 = "X.509";

    /**
     * For instances where the WebLogic HostnameVerifier is used and Wildcard-certificates is not supported.
     * This is the case in e.g WebLogic 10.3.N
     */
    public static class WebLogicHostname implements HostnameVerifier {

        public boolean verify(final String hostname, final SSLSession sslSession) {
            LOGGER.error("Not in use. See DefaultHostname instead.");
            return false;
        }

    }

    /**
     * For instances where the default Javax HostnameVerifier is used and Wildcard-certificates is not supported
     */
    public static class DefaultHostname implements HostnameVerifier {
        public boolean verify(final String hostname, final SSLSession sslSession) {
            if (StringUtils.isNotEmpty(hostname) && sslSession != null) {
                try {
                    final Certificate cert = getFirstCertificate(sslSession.getPeerCertificates());

                    final byte[] encoded = cert.getEncoded();

                    ByteArrayInputStream encodedAsStream = new ByteArrayInputStream(encoded);

                    CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE_X_509);

                    final X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(encodedAsStream);

                    final String commonName = getCanonicalName(x509Certificate.getSubjectDN().getName());

                    LOGGER.info(String.format("Certificate-CommonName: %s", commonName));
                    LOGGER.info(String.format("Certificate-Hostname: %s", hostname));

                    if (commonName.equals(hostname))
                        return true;

                    final Matcher validHostMatcher = VALID_HOST_PATTERN.matcher(commonName);

                    if (validHostMatcher.matches()) {
                        final String regexCn = commonName.replaceAll("\\*", "(.)*");
                        LOGGER.info(String.format("Result of conversion: %s", regexCn));

                        Pattern pattern = Pattern.compile(regexCn);
                        Matcher matcher = pattern.matcher(hostname);

                        if (matcher.matches()) {
                            LOGGER.info("Pattern-matcher OK.");
                            if (matcher.group().equals(hostname)) {
                                LOGGER.info(String.format("Group() matches hostname: %s", matcher.group()));
                                return true;
                            } else {
                                LOGGER.info(String.format("Group() doesn't match hostname: %s", matcher.group()));
                                return false;
                            }
                        } else {
                            LOGGER.error("Pattern-matcher failed.");
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
                return true;
            }
            LOGGER.error("Missing vital parameters. Hostname & Session was empty and/or null.");
            return false;
        }
    }

    /**
     * Get the Canonical Name from the provided subjectDN
     * Canonical Name = CN=SomeText
     *
     * @param subjectDN DN==Distinguished Name
     * @return the canonical name
     */
    private static String getCanonicalName(final String subjectDN) {
        final Matcher matcher = COMMON_NAME_PATTERN.matcher(subjectDN);
        if (matcher.find()) {
            return matcher.group(1);
        }
        LOGGER.warn(String.format("Unable to locate CN in the certificate. Subject was \"%s\"", subjectDN));
        return subjectDN;
    }

    private static Certificate getFirstCertificate(final Certificate[] certificates) {
        if (certificates.length > 0) {
            return certificates[0];
        }
        return null;
    }

}
