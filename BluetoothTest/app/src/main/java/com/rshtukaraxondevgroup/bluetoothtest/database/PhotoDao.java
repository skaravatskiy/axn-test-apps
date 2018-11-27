package com.rshtukaraxondevgroup.bluetoothtest.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<PhotoModel> list);
}
