package com.example.chicksevent.fragment_org;

/**
 * Helper class for building CSV export URLs.
 * This class has NO Android dependencies. It is a pure, simple Java class.
 * 
 * Note: The base URL should ideally be passed as a parameter or loaded from configuration.
 * For now, it's kept here for simplicity, but consider moving to a configuration class.
 * 
 * @author Juan Rea
 */
public class CsvExportHelper {
    // TODO: Consider moving this to a configuration file or passing as parameter
    private static final String BASE_URL = "https://us-central1-listycity-friedchicken.cloudfunctions.net/exportFinalEntrants";

    /**
     * Builds the full CSV export URL from an event ID.
     * This logic is now isolated and easily testable.
     *
     * @param eventId The ID of the event to export.
     * @return The complete URL for the cloud function, or null if the eventId is invalid.
     */
    public static String buildUrl(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            return null;
        }
        return BASE_URL + "?eventId=" + eventId;
    }

    /**
     * Builds the full CSV export URL from an event ID with a custom base URL.
     * This allows the base URL to be passed from Android resources.
     *
     * @param baseUrl The base URL for the cloud function.
     * @param eventId The ID of the event to export.
     * @return The complete URL for the cloud function, or null if parameters are invalid.
     */
    public static String buildUrl(String baseUrl, String eventId) {
        if (baseUrl == null || baseUrl.isEmpty() || eventId == null || eventId.isEmpty()) {
            return null;
        }
        return baseUrl + "?eventId=" + eventId;
    }
}
