package com.rshtukaraxondevgroup.bluetoothtest.presenter;

import com.rshtukaraxondevgroup.bluetoothtest.repository.PhotosRepository;
import com.rshtukaraxondevgroup.bluetoothtest.view.PhotosScreen;

public class PhotoPresenter {
    private PhotosRepository mPhotoRepository;
    private PhotosScreen mPhotosScreen;

    public PhotoPresenter(PhotosScreen photosScreen, PhotosRepository photosRepository) {
        this.mPhotosScreen = photosScreen;
        this.mPhotoRepository = photosRepository;
    }

    public void getPhotosList() {
        mPhotoRepository.getPhotosList()
                .subscribe(list -> mPhotosScreen.addPhotosList(list),
                        throwable -> mPhotosScreen.showError(throwable.getMessage()));
    }

    public void saveReceivedPhotos(byte[] photoModels) {
        mPhotoRepository.saveReceivedPhotos(photoModels);
    }
}
