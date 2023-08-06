package me.viiral.cosmiccore.utils;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.Optional;

public class MiscUtils {

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(value);

        try {
            return Optional.of(Enum.valueOf(enumClass, value));
        } catch (IllegalArgumentException var3) {
            return Optional.empty();
        }
    }

    public static <T> T defaultIfNull(T val, T def) {
        return val != null ? val : def;
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : Preconditions.checkNotNull(second);
    }

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
}
