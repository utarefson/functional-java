package com.func.util;

public class TextUtil {
    /**
     * This method is used to remove special characters
     **/
    public static String cleanText(String text) {
         /*
         // I love this solution, but its execution time is slower than the second approach
         return text.chars()
            .map(c -> { return (c >= 65 && c <= 90) ? (c + 32) : (((c == 32) || (c >= 97 && c <= 122)) ? c : 32); })
            .mapToObj(c -> { return new Character((char)c); })
            .map(Object::toString)
            .collect(Collectors.joining()).trim();
        */
        char[] chars = text.toCharArray();
        char[] newChars = new char[chars.length];
        for (int i=0; i<chars.length; i++) {
            int c = (int)chars[i];
            if (c >= 65 && c <= 90) {
                newChars[i] = (char)(c + 32);
            } else {
                newChars[i] = (char)(((c == 32) || (c >= 97 && c <= 122)) ? c : 32);
            }
        }

        return new String(newChars);
    }

    public static boolean hasContent(String text) {
        return (text != null && text.trim().length() > 0);
    }
}
