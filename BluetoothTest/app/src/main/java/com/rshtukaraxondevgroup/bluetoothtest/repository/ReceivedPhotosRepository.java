package com.rshtukaraxondevgroup.bluetoothtest.repository;

import com.rshtukaraxondevgroup.bluetoothtest.App;
import com.rshtukaraxondevgroup.bluetoothtest.database.PhotoDao;
import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReceivedPhotosRepository {
    private PhotoDao mPhotoDao;

    public ReceivedPhotosRepository() {
        this.mPhotoDao = App.getDatabase().bookDao();
    }

    public Single<List<PhotoModel>> getPhotosListFromDB() {
        return mPhotoDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
