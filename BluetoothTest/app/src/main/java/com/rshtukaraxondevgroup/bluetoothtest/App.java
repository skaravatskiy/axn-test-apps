package com.rshtukaraxondevgroup.bluetoothtest;

import android.app.Application;

import com.rshtukaraxondevgroup.bluetoothtest.database.AppDatabase;

public class App extends Application {
    private static AppDatabase DATABASE;

    @Override
    public void onCreate() {
        super.onCreate();
        DATABASE = AppDatabase.getDatabase(this);
    }

    public static AppDatabase getDatabase() {
        return DATABASE;
    }
}
