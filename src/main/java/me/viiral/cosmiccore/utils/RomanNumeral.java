package me.viiral.cosmiccore.utils;

public class RomanNumeral {
    private static final char[] symbol = new char[] { 'M', 'D', 'C', 'L', 'X', 'V', 'I' };

    private static final int[] value = new int[] { 1000, 500, 100, 50, 10, 5, 1 };

    private static final int[] numbers = new int[] {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9,
            5, 4, 1 };

    private static final String[] letters = new String[] {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX",
            "V", "IV", "I" };

    public static int valueOf(String roman) {
        roman = roman.toUpperCase();
        if (roman.length() == 0)
            return 0;
        for (int i = 0; i < symbol.length; i++) {
            int pos = roman.indexOf(symbol[i]);
            if (pos >= 0)
                return value[i] - valueOf(roman.substring(0, pos)) +
                        valueOf(roman.substring(pos + 1));
        }
        throw new IllegalArgumentException("Invalid Roman Symbol.");
    }

    public static String convertToRoman(int N) {
        String roman = "";
        for (int i = 0; i < numbers.length; i++) {
            while (N >= numbers[i]) {
                roman = roman + letters[i];
                N -= numbers[i];
            }
        }
        return roman;
    }

    public static Integer isInt(String args) {
        try {
            return Integer.parseInt(args);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Double isDouble(String args) {
        try {
            return Double.parseDouble(args);
        } catch (Exception ignore) {
            return null;
        }
    }
}