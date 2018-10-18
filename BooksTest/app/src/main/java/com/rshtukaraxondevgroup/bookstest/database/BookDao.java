package com.rshtukaraxondevgroup.bookstest.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rshtukaraxondevgroup.bookstest.Constants;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BookDao {
    @Query("SELECT * FROM " + Constants.BOOKS_TABLE)
    Single<List<BookModel>> getAll();

    @Query("SELECT * FROM " + Constants.BOOKS_TABLE + " WHERE " + Constants.BOOKS_URL + "= :url")
    Single<BookModel> getByUrl(String url);

    @Insert
    void insert(BookModel bookModel);

    @Insert
    void insertAll(List<BookModel> list);

    @Update
    void update(BookModel bookModel);

    @Delete
    void delete(BookModel bookModel);

    @Query("DELETE FROM " + Constants.BOOKS_TABLE)
    void deleteAll();
}
