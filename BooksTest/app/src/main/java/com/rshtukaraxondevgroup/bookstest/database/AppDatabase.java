package com.rshtukaraxondevgroup.bookstest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rshtukaraxondevgroup.bookstest.model.BookModel;

@Database(entities = {BookModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
