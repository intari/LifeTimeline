package com.viorsan.lifetimeline;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 */

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import net.danlew.android.joda.JodaTimeAndroid;

import de.greenrobot.event.EventBus;
import com.viorsan.lifetimeline.utils.Analytics;
import com.viorsan.lifetimeline.utils.AppSingletons;
import com.viorsan.lifetimeline.utils.Constants;
import com.viorsan.lifetimeline.utils.ReleaseReportLibrary;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends android.app.Application {
    public static final String TAG = MyApplication.class.getName();


    @Override
    public void onCreate() {

        super.onCreate();

        //init logging
        /*
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        */
        Timber.plant(new Timber.DebugTree());
        Timber.tag(TAG);
        Timber.d("OnCreate(logging initialized");

        //init global context singleton, I needed it for getResources,etc
        AppSingletons.setAppContext(getApplicationContext());


        // init Amplitude
        Analytics.initAmplitude();

        //setup default application font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(Constants.DEFAULT_FONT)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        //init Joda Time
        //https://github.com/dlew/joda-time-android/blob/master/library/src/net/danlew/android/joda/DateUtils.java
        JodaTimeAndroid.init(this);

        //init EventBus with our default settings
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG)
                .logNoSubscriberMessages(false)
                .logSubscriberExceptions(true)
                .installDefaultEventBus();


        //create settingsStore
        AppSingletons.getSettingsStore();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            ReleaseReportLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    ReleaseReportLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    ReleaseReportLibrary.logWarning(t);
                }
            }
        }
    }
}