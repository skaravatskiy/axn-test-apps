package com.rshtukaraxondevgroup.bookstest;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.rshtukaraxondevgroup.bookstest.database.AppDatabase;

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
