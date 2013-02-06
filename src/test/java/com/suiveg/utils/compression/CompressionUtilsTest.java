package com.suiveg.utils.compression;

import com.suiveg.utils.encryption.EncryptionUtils;
import org.junit.Test;
import org.powermock.api.easymock.mockpolicies.Log4jMockPolicy;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="vegard.aasen@telenor.com">Vegard Aasen</a>
 */
@MockPolicy(Log4jMockPolicy.class)
@PrepareForTest()
public final class CompressionUtilsTest {

    @Test
    public void test_compress_deflate() {
        final String compressMe = "<samlp:AuthnRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"Sl0A7dTFL05h8RtoDD5YvHLmt83iA7xF\" Version=\"2.0\" IssueInstant=\"2013-02-06T14:00:01Z\" AssertionConsumerServiceIndex=\"0\" AttributeConsumingServiceIndex=\"0\"><saml:Issuer>http://sso.telenor.no</saml:Issuer><samlp:NameIDPolicy AllowCreate=\"true\" Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\" /></samlp:AuthnRequest>";
        final String base64Me = "";
        final String urlEncodeMe = "";
        try {
            final String result = EncryptionUtils.urlEncode(
                    new String(
                    EncryptionUtils.base64(
                            new String(((ByteArrayOutputStream) CompressionUtils.addToZip(compressMe)).toByteArray()), true)),
                    null);
            assertNotNull(result);
            assertTrue(!result.isEmpty());

            final String valid =
                    EncryptionUtils.urlEncode(
                        new String(EncryptionUtils.base64(new String(CompressionUtils.compressWithDeflater(compressMe)), true)),
                        null);
            assertNotNull(valid);

            final String result3 =
                    EncryptionUtils.urlEncode(
                        new String(EncryptionUtils.base64(new String(CompressionUtils.deflate(compressMe)), true)),
                        null);
            assertNotNull(result3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
