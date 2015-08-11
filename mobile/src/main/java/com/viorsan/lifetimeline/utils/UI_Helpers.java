package com.viorsan.lifetimeline.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 */

public class UI_Helpers {

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    /**
     * Method for Setting the Height of the ListView dynamically.
     * Hack to fix the issue of not showing all the items of the ListView
     * when placed inside a ScrollView
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    /**
     * Method for Setting the Height of the ListView dynamically.
     * Version for non-constant-sized listview elements (based off <a href="http://stackoverflow.com/questions/21620764/android-listview-measure-height">http://stackoverflow.com/questions/21620764/android-listview-measure-height</a>
     * Hack to fix the issue of not showing all the items of the ListView
     * when placed inside a ScrollView
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildrenDynamic(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * Shows soft keyboard
     * @param context
     */
    public static void showSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Hides soft keyboard
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * helper to convert object to string even if it's null
     * @param str object to format
     * @return result of .toString or "<null"
     */
    public static String formatPossibleNull(Object str) {
        if (str==null) {
            return "<null>";
        } else {
            return str.toString();
        }
    }

    /**
     * Just vibrate
     */
    public static void vibrate() {
        Vibrator vb = (Vibrator) AppSingletons.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(Constants.DEFAULT_VIBRATE_INTERVAL_MILLIS);
    }

    /**
     * The user has pressed on a virtual on-screen key.
     * @param v view
     */
    public static void hapticFeedbackVirtualKey(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
    }


    /**
     * The user has performed a long press on an object that is resulting
     * in an action being performed.
     * @param v view
     */
    public static void hapticFeedbackLongPress(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
    }

    /**
     * Converts value in dp to value in pixes on current screen
     * @param dp source size in dp
     * @return size in device pixels
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    /**
     * Returns 'extra log description' for HockeyApp error reports
     * See also UpdateAnalytics in ServerConnection
     * @return
     */
    public static String getDescriptionForHockeyApp() {
        String description = "";
        String sep = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BuildId:");
        stringBuilder.append(AppSingletons.getSettingsStore().getBuildId());
        stringBuilder.append(sep);
        stringBuilder.append("DeviceInfoString:");
        stringBuilder.append(AppSingletons.getSettingsStore().getDeviceInfoString());
        stringBuilder.append(sep);

        WifiManager wifiManager = (WifiManager) AppSingletons.getAppContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            stringBuilder.append("WiFi is enabled");
        } else {
            stringBuilder.append("WiFi is disabled");
        }
        stringBuilder.append(sep);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            stringBuilder.append("No WiFi connection info");
        } else {
            stringBuilder.append("Connected to ");
            stringBuilder.append(wifiInfo);
        }
        stringBuilder.append(sep);
        TelephonyManager telephonyManager = (TelephonyManager) AppSingletons.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            stringBuilder.append("No telephony");
            stringBuilder.append(sep);
        } else {
            stringBuilder.append("NetworkCountry:");
            stringBuilder.append(telephonyManager.getNetworkCountryIso());
            stringBuilder.append(sep);
            stringBuilder.append("NetworkOperator");
            stringBuilder.append(telephonyManager.getNetworkOperatorName());
            stringBuilder.append("(");
            stringBuilder.append(telephonyManager.getNetworkOperator());
            stringBuilder.append(")");
            stringBuilder.append(sep);
            stringBuilder.append("SIM Operator name:");
            stringBuilder.append(telephonyManager.getSimOperatorName());
            stringBuilder.append("(");
            stringBuilder.append(telephonyManager.getSimOperator());
            stringBuilder.append(")");
            stringBuilder.append(sep);
            stringBuilder.append("SIMCountry:");
            stringBuilder.append(telephonyManager.getSimCountryIso());
            stringBuilder.append(sep);
        }

        try {
            Process process = Runtime.getRuntime().exec("logcat -t 200 -v time -d com.viorsan *:V");
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }
            bufferedReader.close();

        } catch (IOException e) {
        }
        description = stringBuilder.toString();

        return description;
    }

    /**
     * Returns screen height. Return value is cached and not updates if screen dimensions change
     * This information is used for animations on dashboard
     * @param c
     * @return
     */
    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    /**
     * Return screen width. Return value is cached and not updates if screen dimensions change
     * @param c
     * @return
     */
    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
}
