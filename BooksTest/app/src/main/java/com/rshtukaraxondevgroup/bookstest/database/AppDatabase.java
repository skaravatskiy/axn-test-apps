package com.rshtukaraxondevgroup.bookstest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rshtukaraxondevgroup.bookstest.Constants;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

@Database(entities = {BookModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constants.DATABASE)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
