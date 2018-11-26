package com.rshtukaraxondevgroup.bluetoothtest.repository;


public class PhotoServiceProvider {
    private static IPhotosRepository mRepository;

    private PhotoServiceProvider() {
    }

    public static IPhotosRepository getInstance() {
        if (mRepository == null) {
            mRepository = new PhotosService();
        }
        return mRepository;
    }
}
