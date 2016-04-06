package ru.loftschool.moneytrackerbyoxion;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.activeandroid.ActiveAndroid;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Александр on 08.09.2015.
 */
public class MoneyTrackerApp extends Application {

    private static final String TOKEN_KEY = "token_key";
    private static final String TOKEN_GOOGLE_KEY = "token_google_key";
    public static final String DEFAULT_TOKEN_KEY = "1";
    public static final String USER_NAME = "userName";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ActiveAndroid.initialize(this);
    }

    public static void setUserName(Context context, String name){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public static String getUserName(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(USER_NAME, DEFAULT_TOKEN_KEY);
    }

    public static void setToken(Context context, String token){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(TOKEN_KEY, DEFAULT_TOKEN_KEY);
    }

    public static void setGoogleToken(Context context, String token){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_GOOGLE_KEY, token);
        editor.commit();
    }

    public static String getGoogleToken(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(TOKEN_GOOGLE_KEY, DEFAULT_TOKEN_KEY);
    }
}   

