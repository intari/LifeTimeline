package com.viorsan.lifetimeline.utils;

import com.amplitude.api.Amplitude;
import com.viorsan.lifetimeline.BuildConfig;

import org.json.JSONObject;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 * Now it's possible to disable/enable analytics systems in common way.
 */
public class Analytics {
    public static final String TAG = Analytics.class.getName();



    /**
     * Sets Amplitude's User ID
     * Called by updateAnalyticsProperties in ServerConnection
     * @param userId
     */
    public static void setUserIdForAmplitude(String userId) {
        if (Constants.useAmplitude)
            Amplitude.getInstance().setUserId(userId);
    }

    /**
     * Sets Amplitude's user properties
     * Called by updateAnalyticsProperties in ServerConnection
     * @param userProperties
     */
    public static void setUserPropertiesForAmplitude(JSONObject userProperties) {
        if (Constants.useAmplitude)
            Amplitude.getInstance().setUserProperties(userProperties);

    }
    /**
     * Record Amplitude event
     * @param event
     */
    public static void recordAmplitudeEvent(String event) {
        if (Constants.useAmplitude)
            Amplitude.getInstance().logEvent(event);
    }

    /**
     * Record Amplitude event with properties
     * @param event
     * @param eventProperties
     */
    public static void recordAmplitudeEvent(String event, JSONObject eventProperties) {
        if (Constants.useAmplitude)
            Amplitude.getInstance().logEvent(event,eventProperties);
    }
    /**
     * Init Amplitude analytics (or don't if disabled)
     */
    public static void initAmplitude() {
        if (Constants.useAmplitude) {
            Timber.tag(TAG);
            Timber.d("Initing Amplitude Analytics");
            Amplitude.getInstance().initialize(AppSingletons.getAppContext(), BuildConfig.AMPLITUDE_API_KEY);
            Amplitude.getInstance().enableLocationListening();
        } else {
            Timber.tag(TAG);
            Timber.d("Not initing Amplitude Analytics");
        }

    }
    public static void startAmplitudeSession() {
        if (Constants.useAmplitude)
            Amplitude.getInstance().startSession();
    }
    public static void endAmplitudeSession() {
        if (Constants.useAmplitude)
            Amplitude.getInstance().endSession();
    }

}

