package ru.loftschool.loftblogmoneytracker.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Александр on 08.09.2015.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = true)
public class AppDatabase {

    public final static String NAME = "money_tracker_db";
    public final static int VERSION = 1;
}
