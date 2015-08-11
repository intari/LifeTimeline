package com.viorsan.lifetimeline.utils;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 */
public class Constants {
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final double SECONDS_IN_MINUTE_DOUBLE = 60.0;
    public static final int SECONDS_IN_HOUR=3600;
    public static final int MINUTES_IN_HOUR=60;
    public static final long YEAR_IN_MS = 1000L * 60L * 60L * 24L * 365L;
    public static final String ZERO_ZERO_ZERO_TIME = "00:00:00";
    public static final String FORMAT_HH_MM_SS="%02d:%02d:%02d";
    public static final int HALF_HOUR = 30 *  60;
    public static final int ONE_DAY_IN_HOURS = 24;


    //Calligraphy default font
    public static final String DEFAULT_FONT="fonts/Roboto/Roboto-Regular.ttf";

    //default vibration interval in milliseconds
    public static final long DEFAULT_VIBRATE_INTERVAL_MILLIS=100;

    //GPS Location update intervals
    public static final int LOCATION_UPDATE_INTERVAL_MILLIS = 10000;
    public static final int FASTEST_LOCATION_UPDATE_INTERVAL_MILLIS = 5000;
    //default GPS signal accuracy for use by iconic indicator
    //based on http://en.wikipedia.org/wiki/Dilution_of_precision_(GPS)#Meaning_of_DOP_Values and assuming hdop=accuracy/5
    public static final float GPS_ACCURACY_LEVEL_5 = 2.0f;//best accuracy
    public static final float GPS_ACCURACY_LEVEL_4 = 5.0f;//good accuracy
    public static final float GPS_ACCURACY_LEVEL_3 = 10.0f;//moderate accuracy
    public static final float GPS_ACCURACY_LEVEL_2 = 20.0f;//fair accuracy
    public static final float GPS_ACCURACY_LEVEL_1 = 100.10f;//poor accuracy

    //best accuracy:<2.0f
    //good accuracy:<5.0f
    //moderate accuracy:<10.0f
    //fair accuracy:<20.0f
    //poor accuracy:>20.0f

    public static final boolean useAmplitude=true;//true - use Amplitude Analytics
    public static final int BYTES_IN_KB = 1024;

}
