package com.rshtukaraxondevgroup.bookstest;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.rshtukaraxondevgroup.bookstest.database.AppDatabase;

public class App extends Application {
    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
