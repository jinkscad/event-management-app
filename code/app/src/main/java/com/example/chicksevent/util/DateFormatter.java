package com.example.chicksevent.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Thread-safe date formatting utility class.
 * <p>
 * Uses ThreadLocal to ensure SimpleDateFormat instances are thread-safe.
 * SimpleDateFormat is not thread-safe when used concurrently, so this class
 * provides a safe wrapper for date formatting operations.
 * </p>
 *
 * @author ChicksEvent Team
 */
public class DateFormatter {
    // ThreadLocal ensures each thread has its own SimpleDateFormat instance
    private static final ThreadLocal<SimpleDateFormat> INPUT_FORMAT = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH));
    
    private static final ThreadLocal<SimpleDateFormat> OUTPUT_FORMAT = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH));
    
    private static final ThreadLocal<SimpleDateFormat> MONTH_FORMAT = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("MMM", Locale.ENGLISH));
    
    private static final ThreadLocal<SimpleDateFormat> DAY_FORMAT = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("d", Locale.ENGLISH));

    /**
     * Formats a date string from "MM-dd-yyyy" to "MMM d, yyyy" format.
     *
     * @param dateStr the input date string in "MM-dd-yyyy" format
     * @return formatted date string in "MMM d, yyyy" format, or original string if parsing fails
     */
    public static String formatDatePretty(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        try {
            Date date = INPUT_FORMAT.get().parse(dateStr);
            return OUTPUT_FORMAT.get().format(date);
        } catch (ParseException e) {
            // Return original string if parsing fails
            return dateStr;
        }
    }

    /**
     * Parses a date string and returns separate month and day strings.
     *
     * @param dateStr the input date string in "MM-dd-yyyy" format
     * @return array with [month, day] strings, or null if parsing fails
     */
    public static String[] parseMonthAndDay(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            Date date = INPUT_FORMAT.get().parse(dateStr);
            String month = MONTH_FORMAT.get().format(date);
            String day = DAY_FORMAT.get().format(date);
            return new String[]{month, day};
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Parses a date string and returns a Date object.
     *
     * @param dateStr the input date string in "MM-dd-yyyy" format
     * @return parsed Date object, or null if parsing fails
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            return INPUT_FORMAT.get().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}

