package ru.loftschool.loftblogmoneytracker;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Александр on 08.09.2015.
 */
public class MoneyTrackerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}

