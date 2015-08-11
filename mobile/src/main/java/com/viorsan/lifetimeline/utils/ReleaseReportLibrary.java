package com.viorsan.lifetimeline.utils;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 * TODO:use Amplittude
 */
public final class ReleaseReportLibrary {
    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    public static void logWarning(Throwable t) {
        // TODO report non-fatal warning.

    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }

    private ReleaseReportLibrary() {
        throw new AssertionError("No instances.");
    }
}
