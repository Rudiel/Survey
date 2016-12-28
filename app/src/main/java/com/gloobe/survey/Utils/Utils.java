package com.gloobe.survey.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by rudielavilaperaza on 12/7/16.
 */

public class Utils {

    public static Context context;

    public static void setContext(Context _context) {
        context = _context;
    }

    public static void saveApiKey(String apiKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("API_KEY", apiKey);
        editor.apply();
    }

    public static String getApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("API_KEY", "");
    }

    public static void saveUserId(int id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("USER_ID", id);
        editor.apply();
    }

    public static int getUserID() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("USER_ID", 0);
    }

    public static String getUserMail() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("USER_MAIL", "");
    }

    public static void saveUserMail(String mail) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_MAIL", mail);
        editor.apply();
    }

    public static void saveUserLastName(String lastname) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_LAST", lastname);
        editor.apply();
    }

    public static String getUserLastName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("USER_LAST", "");
    }

    public static void saveUserName(String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_NAME", name);
        editor.apply();
    }

    public static String getUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("USER_NAME", "");
    }

    public static String getImageUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("USER_IMAGE", "");
    }

    public static void saveUserImage(String imagen) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_IMAGE", imagen);
        editor.apply();
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to wifi
                return true;
            } else
                return false;
        } else {
            // not connected to the internet
            return false;
        }

    }
}
