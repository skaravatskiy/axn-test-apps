package com.rshtukaraxondevgroup.bluetoothtest.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

import io.reactivex.Single;

import static com.rshtukaraxondevgroup.bluetoothtest.Constants.PHOTOS_TABLE;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.PHOTOS_URL;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM " + PHOTOS_TABLE)
    Single<List<PhotoModel>> getAll();

    @Query("SELECT * FROM " + PHOTOS_TABLE + " WHERE " + PHOTOS_URL + "= :url")
    Single<PhotoModel> getByUrl(String url);

    @Insert
    void insert(PhotoModel photoModel);

    @Query("DELETE FROM " + PHOTOS_TABLE)
    void deleteAll();
}
