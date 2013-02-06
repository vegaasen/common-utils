package com.suiveg.utils.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utilities that enables users to generate phonetical elements 
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.2-SNAPSHOT
 */
public class PhoneticUtils {

    public static final Map<Character, String> NATO_PHONETIC_LANGUAGE;

    private static final String DEFAULT_SEPARATOR = ",";
    private static final Pattern DEFAULT_MATCHING_PATTERN = Pattern.compile("[a-zA-Z]"); //unused atm..
    private static StringBuilder RESULT;
    private static String separator = DEFAULT_SEPARATOR;
    private static Map<Character, String> PHONETIC_LANGUAGE;

    static {
        NATO_PHONETIC_LANGUAGE = new HashMap<Character, String>();
        NATO_PHONETIC_LANGUAGE.put('a', "Alfa");NATO_PHONETIC_LANGUAGE.put('b', "Bravo");
        NATO_PHONETIC_LANGUAGE.put('c', "Charlie");NATO_PHONETIC_LANGUAGE.put('d', "Delta");
        NATO_PHONETIC_LANGUAGE.put('e', "Echo");NATO_PHONETIC_LANGUAGE.put('f', "Foxtrot");
        NATO_PHONETIC_LANGUAGE.put('g', "Golf");NATO_PHONETIC_LANGUAGE.put('h', "Hotel");
        NATO_PHONETIC_LANGUAGE.put('i', "India");NATO_PHONETIC_LANGUAGE.put('j', "Juliet");
        NATO_PHONETIC_LANGUAGE.put('k', "Kilo");NATO_PHONETIC_LANGUAGE.put('l', "Lima");
        NATO_PHONETIC_LANGUAGE.put('m', "Mike");NATO_PHONETIC_LANGUAGE.put('n', "November");
        NATO_PHONETIC_LANGUAGE.put('o', "Oscar");NATO_PHONETIC_LANGUAGE.put('p', "Papa");
        NATO_PHONETIC_LANGUAGE.put('q', "Quebec");NATO_PHONETIC_LANGUAGE.put('r', "Romeo");
        NATO_PHONETIC_LANGUAGE.put('s', "Sierra");NATO_PHONETIC_LANGUAGE.put('t', "Tango");
        NATO_PHONETIC_LANGUAGE.put('u', "Uniform");NATO_PHONETIC_LANGUAGE.put('v', "Victor");
        NATO_PHONETIC_LANGUAGE.put('w', "Whiskey");NATO_PHONETIC_LANGUAGE.put('x', "X-ray");
        NATO_PHONETIC_LANGUAGE.put('y', "Yankee");NATO_PHONETIC_LANGUAGE.put('z', "Zulu");
        PHONETIC_LANGUAGE = NATO_PHONETIC_LANGUAGE;
    }

    private PhoneticUtils(){}

    public static String convertToPhonetic(final String segment) {
        return convertToPhonetic(segment, DEFAULT_SEPARATOR);
    }

    /**
     * When using this method, the result will not contain any special characters, nor spaces.
     *
     * @param segment String to evaluate and refactor with phonetic values
     * @param s separator
     * @return phonetic generated string from the selected segment.
     */
    public static String convertToPhonetic(final String segment, String s) {
        if(StringUtils.isNotBlank(segment)) {
            RESULT = new StringBuilder();
            setSeparator(((StringUtils.isBlank(s) ? DEFAULT_SEPARATOR : s)));
            final String result = segment.trim();
            for(char ch : result.toCharArray()) {
                String phonetic = PHONETIC_LANGUAGE.get(Character.toLowerCase(ch));
                //could be a interface, but nah :-) "makeItEasy"
                if(Character.isDigit(ch)) {
                    phonetic = String.valueOf(ch);
                }
                if(Character.isLowerCase(ch)) {
                    phonetic = phonetic.toLowerCase();
                }
                if(Character.isUpperCase(ch)) {
                    phonetic = phonetic.toUpperCase();
                }
                if(StringUtils.isNotBlank(phonetic)) {
                    addToStringBuilder(phonetic);
                    addToStringBuilder(separator);
                }
            }
            cleanLastElementInStringBuilder();
            return RESULT.toString();
        }
        throw new IllegalArgumentException("Segment cannot be null or blank.");
    }

    /**
     * The default phonetic language is NATO_PHONETIC_LANGUAGE. If another one is wanted, please set it here
     *
     * @param pl phonetic language of choice
     */
    public static void setPhoneticLanguage(final Map<Character, String> pl) {
        PHONETIC_LANGUAGE = pl;
    }

    private static void addToStringBuilder(Object o) {
        if(RESULT!=null) {
            RESULT.append(o);
        }
    }

    private static void cleanLastElementInStringBuilder() {
        if(RESULT!=null && RESULT.length()>0) {
            RESULT.setLength(RESULT.length() - 1);
        }
    }

    private static void setSeparator(final String s) {
        separator = s;
    }

}