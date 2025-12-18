package com.example.chicksevent.util;

import android.util.Log;

/**
 * Utility class for conditional logging that can be disabled in release builds.
 * <p>
 * This helps reduce log noise in production while keeping useful logs during development.
 * All debug and info logs are automatically disabled in release builds.
 * </p>
 */
public class LogUtils {
    private LogUtils() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Whether debug logging is enabled.
     * Set to false in release builds to disable all debug/info logs.
     */
    private static final boolean DEBUG = true; // TODO: Set to BuildConfig.DEBUG in production
    
    /**
     * Logs a debug message only if debug logging is enabled.
     * 
     * @param tag the log tag
     * @param msg the message to log
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }
    
    /**
     * Logs a debug message with exception only if debug logging is enabled.
     * 
     * @param tag the log tag
     * @param msg the message to log
     * @param tr the exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, msg, tr);
        }
    }
    
    /**
     * Logs an info message only if debug logging is enabled.
     * 
     * @param tag the log tag
     * @param msg the message to log
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
    
    /**
     * Logs an info message with exception only if debug logging is enabled.
     * 
     * @param tag the log tag
     * @param msg the message to log
     * @param tr the exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, msg, tr);
        }
    }
    
    /**
     * Logs a warning message (always enabled, even in release).
     * 
     * @param tag the log tag
     * @param msg the message to log
     */
    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }
    
    /**
     * Logs a warning message with exception (always enabled).
     * 
     * @param tag the log tag
     * @param msg the message to log
     * @param tr the exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }
    
    /**
     * Logs an error message (always enabled, even in release).
     * 
     * @param tag the log tag
     * @param msg the message to log
     */
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
    
    /**
     * Logs an error message with exception (always enabled).
     * 
     * @param tag the log tag
     * @param msg the message to log
     * @param tr the exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }
}

