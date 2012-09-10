package com.suiveg.utils.http;

import com.suiveg.utils.abs.AbstractUtil;
import com.suiveg.utils.http.model.HttpStatusCodes;
import com.suiveg.utils.string.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Date;

import static com.suiveg.utils.encryption.EncryptionUtils.convertToHex;
import static com.suiveg.utils.encryption.EncryptionUtils.encodeWithBase64;
import static com.suiveg.utils.file.FileUtils.getFileOutputStream;

/**
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.2
 */
public final class HttpUtils extends AbstractUtil {

    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);
    private static final String
            URL_SLASH = "/",
            DEFAULT_PWD_UNAME_SEPERATOR = ":";

    private HttpUtils() {}

    public static synchronized File getFileFromURL(final URL sourceUrl, File target)
            throws IOException, IllegalArgumentException {
        if (verifyNotNull(sourceUrl)) {
            return getFileUsingURL(sourceUrl, target);
        }
        throw new IllegalArgumentException("String is empty, missing or illegal.");
    }

    public static synchronized File getFileFromURI(final URI sourceUri, File target)
            throws IOException, IllegalArgumentException {
        if (verifyNotNull(sourceUri)) {
            return getFileUsingURL(sourceUri.toURL(), target);
        }
        throw new IllegalArgumentException("String is empty, missing or illegal.");
    }

    public static synchronized InputStream getInputStreamFromURL(final URL sourceUrl)
            throws IOException, NullPointerException {
        if (verifyNotNull(sourceUrl)) {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) sourceUrl.openConnection();
            if (httpUrlConnection.getResponseCode() != HttpStatusCodes.OK_FOUND.getCode()) {
                return httpUrlConnection.getInputStream();
            }
            throw new ConnectException(String.format("Could not fetch InputStream. Http returned status %s, not %s",
                    httpUrlConnection.getResponseCode(), HttpStatusCodes.OK_FOUND.getCode()));
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    public static String getFileName(final URL url) throws NullPointerException {
        if (verifyNotNull(url)) {
            String path = url.getFile();
            if (StringUtils.isNotEmpty(path)) {
                String[] splittedPath = path.split(URL_SLASH);
                if (splittedPath.length > 0) {
                    return splittedPath[splittedPath.length - 1];
                }
            }
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    public static String getFileExtension(final URL url) throws NullPointerException {
        if (verifyNotNull(url)) {
            String location = (
                    (StringUtils.isNotEmpty(url.getFile())) ?
                            url.getFile() :
                            (StringUtils.isNotEmpty(url.getPath()) ?
                                    url.getPath() :
                                    ""));
            if (StringUtils.isNotBlank(location)) {
                if (location.contains(".")) {
                    return location.substring(location.lastIndexOf("."), location.length() - 1);
                }
            }
            throw new Error("Unable to parse path, it might be null or empty. " +
                    "Reconfirm the URL-object. [URL was not null]");
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    /**
     * Provides support to get the inputstream from a url providing a username, password
     *
     * @param username Username
     * @param password Password
     * @param url URL
     * @return InputStream from result
     */
    public static InputStream getInputStreamFromUrl(final String username, final String password, final URL url) {
        if (verifyNotNull(username, password, url)) {
            if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
                try {
                    final URLConnection connection = url.openConnection();
                    String encodedUsernamePassword = new String(
                            encodeWithBase64(username + DEFAULT_PWD_UNAME_SEPERATOR + password, true)
                    );

                    if (connection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
                        httpsURLConnection.setRequestProperty("Authorization",
                                                        String.format("Basic %s", encodedUsernamePassword));
                        httpsURLConnection.setRequestMethod("GET");
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setDoOutput(true);
                        httpsURLConnection.setUseCaches(false);
                        httpsURLConnection.connect();
                        return httpsURLConnection.getInputStream();
                    } else if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                        httpURLConnection.setRequestProperty("Authorization",
                                                        String.format("Basic %s", encodedUsernamePassword));
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setUseCaches(false);
                        httpURLConnection.connect();
                        return httpURLConnection.getInputStream();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new UnknownError("Unable to recieve username/password.");
                }
            }
            throw new IllegalArgumentException("Username or password is empty or null.");
        }
        throw new IllegalArgumentException("Missing required arguments.");
    }

    private static File getFileUsingURL(URL sourceUrl, File target) throws IOException {
        if (verifyNull(target)) {
            target = new File(
                    TEMP_DIRECTORY +
                    File.separator +
                    new String(convertToHex((
                            DEFAULT_STRING +
                            new Date().getTime()
                    ).getBytes()))
            );
        }
        InputStream urlStream = sourceUrl.openStream();
        FileOutputStream targetStream = getFileOutputStream(target);
        try {
            IOUtils.copy(urlStream, targetStream);
            IOUtils.closeQuietly(targetStream);
        } catch (IOException e) {
            LOGGER.error(String.format("Unable to copy the opened stream for {%s}", "{..link..}"));
            throw new IOException();
        } finally {
            IOUtils.closeQuietly(targetStream);
        }
        return target;
    }

}
