package com.viorsan.lifetimeline.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.seppius.i18n.plurals.PluralResources;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import timber.log.Timber;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 * Contexts,etc
 */
public class AppSingletons {
    public static final String TAG = AppSingletons.class.getName();

    private static Context appContext;
    public static void setAppContext(Context context) {
        appContext=context;
    }
    public static Context getAppContext() {
        return appContext;
    }

    private static JobManager jobManager;

    private static Typeface roubleSupportedTypeface=null;
    public synchronized static Typeface getRoubleSupportedTypeface() {
        if (roubleSupportedTypeface==null) {
            roubleSupportedTypeface=Typeface.createFromAsset(AppSingletons.getAppContext().getAssets(), "fonts/rouble2.ttf");
        }
        return roubleSupportedTypeface;
    }
    private static Typeface robotoRegularTypeface =null;
    public synchronized static Typeface getRobotoRegularTypeface() {
        if (robotoRegularTypeface ==null) {
            robotoRegularTypeface =Typeface.createFromAsset(AppSingletons.getAppContext().getAssets(), "fonts/Roboto/Roboto-Regular.ttf");
        }
        return robotoRegularTypeface;
    }
    private static Typeface robotoLightTypeface =null;
    public synchronized static Typeface getRobotoLightTypeface() {
        if (robotoLightTypeface ==null) {
            robotoLightTypeface =Typeface.createFromAsset(AppSingletons.getAppContext().getAssets(), "fonts/Roboto/Roboto-Light.ttf");
        }
        return robotoLightTypeface;
    }
    private static Typeface robotoMediumTypeface =null;
    public synchronized static Typeface getRobotoMediumTypeface() {
        if (robotoMediumTypeface==null) {
            robotoMediumTypeface=Typeface.createFromAsset(AppSingletons.getAppContext().getAssets(), "fonts/Roboto/Roboto-Medium.ttf");
        }
        return robotoMediumTypeface;
    }
    private static Typeface robotoBoldTypeface =null;
    public synchronized static Typeface getRobotoBoldTypeface() {
        if (robotoBoldTypeface==null) {
            robotoBoldTypeface=Typeface.createFromAsset(AppSingletons.getAppContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        }
        return robotoBoldTypeface;
    }

    private static SettingsStore settingsStore;

    /**
     * provides access to singleton for local settings store
     * @return settingsStore to use (will be created if necessary)
     */
    public synchronized static SettingsStore getSettingsStore() {
        if (settingsStore==null) {
            Timber.tag(TAG);
            Timber.d("Creating settingsStore");
            settingsStore=new SettingsStore(getAppContext());
        }
        return settingsStore;
    }
    public synchronized static JobManager getJobManager() {
        if (jobManager!=null) {
            return jobManager;
        } else {
            Configuration configuration = new Configuration.Builder(getAppContext())
                    .customLogger(new CustomLogger() {
                        private static final String TAG = "JobManager";
                        @Override
                        public boolean isDebugEnabled() {
                            return true;
                        }

                        @Override
                        public void d(String text, Object... args) {
                            Timber.tag(TAG);
                            Timber.d(String.format(text, args));
                        }

                        @Override
                        public void e(Throwable t, String text, Object... args) {
                            Timber.tag(TAG);
                            Timber.e(String.format(text, args), t);
                        }

                        @Override
                        public void e(String text, Object... args) {
                            Timber.tag(TAG);
                            Timber.e(String.format(text, args));
                        }
                    })
                    .minConsumerCount(1)//always keep at least one consumer alive
                    .maxConsumerCount(3)//up to 3 consumers at a time
                    .loadFactor(3)//3 jobs per consumer
                    .consumerKeepAlive(120)//wait 2 minute
                            //.networkUtil(new Connection check()) //assume network is here if websocket connection is alive
                    .build();
            jobManager = new JobManager(getAppContext(), configuration);
            return jobManager;
        }
    }

    /**
     * Returns our locale
     * Right now just ask system for default locale
     * @return app's locale
     */
    public static Locale getAppLocale() {
        return Locale.getDefault();
    }


    private static PluralResources pluralResources;

    /**
     * Returns helper for use with Quanitity strings which works on all API levels we need (including API 10 or lower)
     * Helper uses https://bintray.com/populov/maven/com.seppius.plurals:android-i18n-plurals#read
     * See https://github.com/intari/android-i18n-plurals for short readme
     * See http://www.dimasokol.ru/plurals-in-android/ for more through explanation why it's needed
     * @param context
     * @return
     */
    public synchronized static PluralResources getPluralResources(Context context) {
        if (pluralResources==null) {
            try {
                pluralResources = new PluralResources( context.getResources() );
            } catch (SecurityException ex) {
                Timber.e("getPluralResources():SecurityException:" + ex.toString());
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                Timber.e(ex, "getPluralResources():NoSecurityExceptionSecurityException:" + ex.toString());
            }
        }
        return pluralResources;
    }

}

