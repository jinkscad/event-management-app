package com.example.chicksevent.util;

/**
 * Application-wide constants.
 * <p>
 * Centralizes magic numbers, strings, and configuration values used throughout the app.
 * This makes the code more maintainable and easier to modify.
 * </p>
 *
 * @author ChicksEvent Team
 */
public class AppConstants {

    private AppConstants() {
        // Utility class - prevent instantiation
    }

    /**
     * Default value representing unlimited entrants for an event.
     * Used when no entrant limit is specified.
     */
    public static final int UNLIMITED_ENTRANTS = 999;

    /**
     * Date format used for input (e.g., from EditText fields).
     * Format: "MM-dd-yyyy" (e.g., "10-30-2025")
     */
    public static final String DATE_FORMAT_INPUT = "MM-dd-yyyy";

    /**
     * Date format used for display output.
     * Format: "MMM d, yyyy" (e.g., "Oct 30, 2025")
     */
    public static final String DATE_FORMAT_DISPLAY = "MMM d, yyyy";

    /**
     * Date format for month abbreviation.
     * Format: "MMM" (e.g., "Oct")
     */
    public static final String DATE_FORMAT_MONTH = "MMM";

    /**
     * Date format for day of month.
     * Format: "d" (e.g., "30")
     */
    public static final String DATE_FORMAT_DAY = "d";

    /**
     * Time format used for input.
     * Format: "HH:mm" (e.g., "14:30")
     */
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Time format pattern for validation (regex).
     * Matches: "HH:MM" where H and M are digits
     */
    public static final String TIME_FORMAT_PATTERN = "\\d{2}:\\d{2}";

    /**
     * Maximum length for event names.
     */
    public static final int MAX_EVENT_NAME_LENGTH = 100;

    /**
     * Maximum length for event descriptions.
     */
    public static final int MAX_EVENT_DESCRIPTION_LENGTH = 1000;

    /**
     * Maximum length for event tags.
     */
    public static final int MAX_TAG_LENGTH = 50;

    /**
     * Location timeout in milliseconds (30 seconds).
     * Used when requesting location updates.
     */
    public static final long LOCATION_TIMEOUT_MS = 30000;

    /**
     * Location permission request code.
     */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
}

