package com.egorkivilev.checkapp.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    // Formats unix time into readable format for user
    public static String formatUnixTime(long unixMillis) {
        LocalDateTime dateTime = Instant.ofEpochMilli(unixMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy @ h:mm a");
        return dateTime.format(formatter);
    }
}
