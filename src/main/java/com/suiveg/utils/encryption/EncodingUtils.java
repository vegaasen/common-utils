package com.suiveg.utils.encryption;

import com.suiveg.utils.abs.AbstractUtil;
import com.suiveg.utils.encryption.algorithms.Crypt;
import com.suiveg.utils.string.StringUtils;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Tools to encrypt / decrypt elements using both well known and not-so-well known routines (also known as self-made..)
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.1
 */
public final class EncodingUtils extends AbstractUtil {

    public static final String
            UTF_8 = "UTF-8",
            ISO_8851_1 = "ISO-8859-1";

    private EncodingUtils() {}
    
    /**
     * Generate a Crypt hashed string based on a salt and a string (usually a password)
     * This can be used together with e.g generating a Apache HTTPD password for Authentication
     *
     * @param salt (optional) the salt ([aA-zZ][0-9]\.)
     * @param string (required) the inputstring (usually a password)
     * @return The hashed string
     * @throws NullPointerException _
     */
    public static String generateCryptHashedString(final String salt, final String string) throws NullPointerException{
        if(verifyNotNull(string)) {
            return Crypt.cryptUsingStandardDES(salt, string);
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    /**
     * Simple digester. Takes two arguments ; sentence and encryptionType
     *
     * @param sentence _
     * @param encryptionType _
     * @return _
     * @throws NullPointerException _
     */
    public static byte[] convertToDigest(final byte[] sentence, final EncryptionType encryptionType)
            throws NullPointerException {
        if (verifyNotNull(sentence, encryptionType)) {
            if(sentence.length>0) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance(encryptionType.toString());
                    messageDigest.update(sentence);
                    return messageDigest.digest();
                } catch (NoSuchAlgorithmException e) {
                    throw new UnsupportedOperationException("Could not process item.");
                }
            }
            return sentence;
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    /**
     * Encode or Decode with base64.
     *
     * todo: not working as intended?!
     *
     * @param string the string to parse
     * @param encode true|false - false == decode
     * @return byte array
     * @throws Exception _
     */
    public static byte[] base64(final String string, boolean encode) throws Exception {
        byte[] bytesOfInput = string.getBytes();
        if(encode) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStream b64os = MimeUtility.encode(
                    outputStream,
                    EncryptionType.BASE_64.toString());
            b64os.write(bytesOfInput);
            b64os.close();
            return outputStream.toByteArray();
        }else{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesOfInput);
            InputStream b64is = MimeUtility.decode(
                    inputStream,
                    EncryptionType.BASE_64.toString());
            byte[] tmp = new byte[bytesOfInput.length];
            int n = b64is.read(tmp);
            byte[] res = new byte[n];
            System.arraycopy(tmp, 0, res, 0, n);
            return res;
        }
     }

    public static String base64Encode(final String string) {
        if(verifyNotNull(string)) {
            try {
                return DatatypeConverter.printBase64Binary(string.getBytes(UTF_8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("");
    }

    public static byte[] base64Decode(final String string) {
        if(verifyNotNull(string)) {
            return DatatypeConverter.parseBase64Binary(string);
        }
        throw new IllegalArgumentException("");
    }

    public static String urlEncode(final String string, String encoding) {
        if(verifyNotNull(string)) {
            if(encoding==null || encoding.isEmpty()) {
                encoding = UTF_8;
            }
            try {
                return URLEncoder.encode(string, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return string;
        }
        throw new IllegalArgumentException("");
    }

    public static String urlDecode(final String string, String encoding) {
        if(verifyNotNull(string)) {
            if(encoding==null || encoding.isEmpty()) {
                encoding = UTF_8;
            }
            try {
                return URLDecoder.decode(string, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return string;
        }
        throw new IllegalArgumentException("");
    }


    /**
     * Simple byte array to hex byte array converter
     *
     * @param sentence _
     * @return _
     * @throws NullPointerException _
     */
    public static byte[] convertToHex(final byte[] sentence)
            throws NullPointerException {
        if (verifyNotNull(sentence)) {
            if(sentence.length>0) {
                StringBuilder sb = new StringBuilder();
                for (byte b : sentence) {
                    sb.append(
                            Integer.toString(
                                    (b & 0xff) + 0x100,
                                    16
                            ).substring(1)
                    );
                }
                return sb.toString().getBytes();
            }
            return sentence;
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    /**
     * Simple String to hex converter
     *
     * @param hexSentence _
     * @return _
     * @throws NullPointerException _
     */
    public static byte[] convertFromHex(final String hexSentence)
            throws NullPointerException {
        if (verifyNotNull(hexSentence)) {
            if(StringUtils.isNotBlank(hexSentence)) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hexSentence.length() - 1; i += 2) {
                    String output = hexSentence.substring(i, (i + 2));
                    int decimal = Integer.parseInt(output, 16);
                    //Note: Dah! Ofc is char important! Without it, you will get its ascii reference!
                    sb.append((char) decimal);
                }
                return sb.toString().getBytes();
            }
            return hexSentence.getBytes();
        }
        throw new NullPointerException(E_OBJECT_WAS_NULL);
    }

    public enum EncryptionType {
        MD_5("MD5"),
        SHA_1("SHA-1"),
        MD_2("MD2"),
        BASE_64("base64");

        private String encryptionType;

        EncryptionType(String encryptionType) {
            this.encryptionType = encryptionType;
        }

        @Override
        public String toString() {
            return encryptionType;
        }
    }

}
