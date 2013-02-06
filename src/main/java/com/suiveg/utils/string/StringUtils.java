package com.suiveg.utils.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple StringUtils-class that can be used to check numerous different
 * variants with Strings. Similar to the commons-lang, but its a part of a much smaller package
 *
 * Todo: complete all methods, some of them are partly unfinished
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.2-SNAPSHOT
 */
public final class StringUtils {

    private StringUtils() {}

    public static boolean equals(final String s1, final String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        return s1.compareTo(s2) == 0;
    }

    public static int indexOfIgnoreCase(final String s, final String what, final int from) {
        if (s == null || what == null) {
            return -1;
        }
        return s.toLowerCase().indexOf(what.toLowerCase(), from);
    }

    public static String stringOfChars(final char c, final int n) {
        final StringBuilder sb = new StringBuilder(n);
        for (int q = n - 1; q >= 0; q--) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String reverseTrim(final String s) {
        if (s == null) {
            return null;
        }
        int len = s.length();
        if (len == 0 || !Character.isWhitespace(s.charAt(len - 1))) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s);
        while (len > 0 && Character.isWhitespace(sb.charAt(len - 1))) {
            sb.setLength(--len);
        }
        return sb.toString();
    }

    public static String trim(final String s) {
        if (s == null) {
            return null;
        }
        return s.trim();
    }

    public static String trimToNull(final String s) {
        if (s == null) {
            return null;
        }
        final String trimmed = s.trim();
        if (trimmed.length() == 0) {
            return null;
        }
        return trimmed;
    }

    public static String trimToEmpty(final String s) {
        if (s == null) {
            return "";
        }
        return s.trim();
    }

    public static boolean isAllUpperCase(String s) {
        return false;
    }

    public static boolean isAllLowerCase(String s) {
        return false;
    }

    public static boolean isFirstLetterUpperCase(String s) {
        return s != null && isAllUpperCase(((Character) s.charAt(0)).toString());
    }

    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(final String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isBlank(final String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isNotBlank(final String s) {
        return s != null && s.trim().length() > 0 && !s.equals("");
    }

    public static boolean isNumerical(final String s) {
        return s != null;
    }

    public static boolean isWhitespace(final String s) {
        if (s != null) {
            if (s.length() > 1) {
                boolean found = false;
                for (char c : s.toCharArray()) {
                    if (!((Character) c).toString().equals(" ")) {
                        found = true;
                        break;
                    }
                }
                return found;
            } else {
                return ((Character) s.charAt(0)).toString().equals(" ");
            }
        }
        return false;
    }

    public static String reverse(final String s) {
        if (s != null) {
            return new StringBuffer(s).reverse().toString();
        }
        return "";
    }

    public static String removeAllNonNumericCharacters(final String s) {
        if (s != null) {
            return s.replaceAll("[^0-9]", "");
        }
        return "";
    }

    public static String decapitalise(final String s) {
        if (s != null) {
            List<String> newString = new ArrayList<String>();
            for (String st : s.split(" ")) {
                if (isAllUpperCase(((Character) st.charAt(0)).toString())) {
                    newString.add(st.toLowerCase());
                }
            }
            String genString = "";
            for (String st : newString) {
                genString += st;
                if (newString.indexOf(st) != newString.size()) {
                    genString += " ";
                }
            }
            return genString;
        }
        return "";
    }

    public static String remove(final String s, final char charToRemove) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(charToRemove) < 0) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        for (int q = 0; q < s.length(); q++) {
            final char c = s.charAt(q);
            if (c != charToRemove) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
