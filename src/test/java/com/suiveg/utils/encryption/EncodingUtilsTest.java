package com.suiveg.utils.encryption;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.powermock.api.easymock.mockpolicies.Log4jMockPolicy;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import java.security.MessageDigest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@MockPolicy(Log4jMockPolicy.class)
@PrepareForTest({IOUtils.class, MessageDigest.class, StringBuffer.class})
public class EncodingUtilsTest {

    private static final String
            CONVERTED_STRING_THROUGH_MD5 = "ConvertedStringThroughMD5";
    private static final String
            SIMPLE_STRING = CONVERTED_STRING_THROUGH_MD5,
            EXPECTED_RESULT_OF_CONVERTED_SIMPLE_STRING = "436f6e766572746564537472696e675468726f7567684d4435";

    @Before
    public void setUp() {
        //unset atm.. not using powermock or any of that "fancy stuff" as everything is statically called!
    }

    @Test(enabled = true, alwaysRun = true, testName = "")
    public void convertPasswordWithCrypt_allowedSalt() {
        
    }

    @Test(enabled = true, alwaysRun = true, testName = "MD5 Test")
    public void convertToDigest_provideMD5() {
        byte[] result = EncodingUtils.convertToDigest(CONVERTED_STRING_THROUGH_MD5.getBytes(), EncodingUtils.EncryptionType.MD_5);
        assertNotNull(result);
        assertTrue(result.length>0);
    }

    @Test(enabled = true, alwaysRun = true, testName = "MD5 Test")
    public void convertToDigest_provideMD2() {
        byte[] result = EncodingUtils.convertToDigest(CONVERTED_STRING_THROUGH_MD5.getBytes(), EncodingUtils.EncryptionType.MD_2);
        assertNotNull(result);
        assertTrue(result.length>0);
    }

    @Test(enabled = true, alwaysRun = true, testName = "MD5 Test")
    public void convertToDigest_provideSHA_1() {
        byte[] result = EncodingUtils.convertToDigest(CONVERTED_STRING_THROUGH_MD5.getBytes(), EncodingUtils.EncryptionType.SHA_1);
        assertNotNull(result);
        assertTrue(result.length>0);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void convertToDigest_provideNullObject() {
        EncodingUtils.convertToDigest(null, EncodingUtils.EncryptionType.MD_5);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void convertToDigest_provideNullEncryptionType() {
        EncodingUtils.convertToDigest(CONVERTED_STRING_THROUGH_MD5.getBytes(), null);
    }

    @Test
    public void convertToHex_simpleString() {
        byte[] result = EncodingUtils.convertToHex(SIMPLE_STRING.getBytes());
        assertNotNull(result);
        assertTrue(result.length>0);
        assertEquals(new String(result), EXPECTED_RESULT_OF_CONVERTED_SIMPLE_STRING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void convertToHex_nullArgument() {
        EncodingUtils.convertToHex(null);
    }

    @Test
    public void convertToHex_emptyString() {
        EncodingUtils.convertToHex("".getBytes());
    }

    @Test
    public void convertFromHex_simpleString() {
        byte[] result = EncodingUtils.convertFromHex(EXPECTED_RESULT_OF_CONVERTED_SIMPLE_STRING);
        assertNotNull(result);
        assertTrue(result.length>0);
        assertEquals(SIMPLE_STRING, new String(result));
    }

    @Test
    public void encodeWithBase64() {
        String textToEncrypt = "<samlp:AuthnRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"Sl0A7dTFL05h8RtoDD5YvHLmt83iA7xF\" Version=\"2.0\" IssueInstant=\"2013-02-06T14:00:01Z\" AssertionConsumerServiceIndex=\"0\" AttributeConsumingServiceIndex=\"0\"><saml:Issuer>http://sso.telenor.no</saml:Issuer><samlp:NameIDPolicy AllowCreate=\"true\" Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\" /></samlp:AuthnRequest>";
        try {
            byte[] result = EncodingUtils.base64(textToEncrypt, true);
            String expected = "PHNhbWxwOkF1dGhuUmVxdWVzdCB4bWxuczpzYW1scD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOnByb3RvY29sIiB4bWxuczpzYW1sPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YXNzZXJ0aW9uIiBJRD0iU2wwQTdkVEZMMDVoOFJ0b0RENVl2SExtdDgzaUE3eEYiIFZlcnNpb249IjIuMCIgSXNzdWVJbnN0YW50PSIyMDEzLTAyLTA2VDE0OjAwOjAxWiIgQXNzZXJ0aW9uQ29uc3VtZXJTZXJ2aWNlSW5kZXg9IjAiIEF0dHJpYnV0ZUNvbnN1bWluZ1NlcnZpY2VJbmRleD0iMCI+PHNhbWw6SXNzdWVyPmh0dHA6Ly9zc28udGVsZW5vci5ubzwvc2FtbDpJc3N1ZXI+PHNhbWxwOk5hbWVJRFBvbGljeSBBbGxvd0NyZWF0ZT0idHJ1ZSIgRm9ybWF0PSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6bmFtZWlkLWZvcm1hdDp0cmFuc2llbnQiIC8+PC9zYW1scDpBdXRoblJlcXVlc3Q+";
            assertNotNull(result);
            String secondResult = EncodingUtils.base64Encode(textToEncrypt);
            assertNotNull(secondResult);
            assertEquals(expected, secondResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {

    }

}
