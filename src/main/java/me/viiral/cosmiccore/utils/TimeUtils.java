package me.viiral.cosmiccore.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static String formatSeconds(long seconds) {
        if (seconds == 0L)
            return "0s";
        long day = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - day * 24L;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long secs = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        StringBuilder sb = new StringBuilder();
        if (day > 0L)
            sb.append(day).append("d").append(" ");
        if (hours > 0L)
            sb.append(hours).append("h").append(" ");
        if (minutes > 0L)
            sb.append(minutes).append("m").append(" ");
        if (secs > 0L)
            sb.append(secs).append("s");
        String diff = sb.toString();
        return diff.isEmpty() ? "Now" : diff.trim();
    }
}