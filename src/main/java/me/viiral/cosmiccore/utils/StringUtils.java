package me.viiral.cosmiccore.utils;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String toNiceString(String string) {
        string = ChatColor.stripColor(string).replace('_', ' ').toLowerCase();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < string.toCharArray().length; ++i) {
            char c = string.toCharArray()[i];
            if (i > 0) {
                char prev = string.toCharArray()[i - 1];
                if ((prev == ' ' || prev == '[' || prev == '(') && (i == string.toCharArray().length - 1 || c != 'x' || !Character.isDigit(string.toCharArray()[i + 1]))) {
                    c = Character.toUpperCase(c);
                }
            } else if (c != 'x' || !Character.isDigit(string.toCharArray()[i + 1])) {
                c = Character.toUpperCase(c);
            }

            sb.append(c);
        }

        return sb.toString();
    }

    public static String flipString(String string) {
        StringBuilder input = new StringBuilder();
        input.append(string);
        input.reverse();
        return input.toString();
    }
}
