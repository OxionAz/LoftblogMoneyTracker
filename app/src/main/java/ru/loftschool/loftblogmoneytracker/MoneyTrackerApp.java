package ru.loftschool.loftblogmoneytracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.activeandroid.ActiveAndroid;

/**
 * Created by Александр on 08.09.2015.
 */
public class MoneyTrackerApp extends Application {

    private static final String TOKEN_KEY = "token_key";

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    public static void setToken(Context context, String token){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(TOKEN_KEY, "1");
    }
}

