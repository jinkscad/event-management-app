package com.example.chicksevent.util;

import androidx.annotation.Nullable;

/**
 * Utility class for string operations.
 * <p>
 * Provides common string manipulation methods used throughout the application.
 * </p>
 *
 * @author ChicksEvent Team
 */
public class StringUtils {

    /**
     * Safely converts a CharSequence to a trimmed string.
     * <p>
     * Returns an empty string if the input is null, otherwise returns the trimmed string.
     * This is commonly used when reading text from EditText fields.
     * </p>
     *
     * @param cs the input character sequence (can be null)
     * @return trimmed string, or empty string if input is null
     */
    public static String trim(@Nullable CharSequence cs) {
        return cs == null ? "" : cs.toString().trim();
    }

    /**
     * Checks if a CharSequence is null or empty after trimming.
     *
     * @param cs the input character sequence
     * @return true if the string is null, empty, or contains only whitespace
     */
    public static boolean isEmpty(@Nullable CharSequence cs) {
        return cs == null || cs.toString().trim().isEmpty();
    }

    /**
     * Checks if a CharSequence is not null and not empty after trimming.
     *
     * @param cs the input character sequence
     * @return true if the string is not null and contains non-whitespace characters
     */
    public static boolean isNotEmpty(@Nullable CharSequence cs) {
        return !isEmpty(cs);
    }
}

