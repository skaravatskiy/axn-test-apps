package com.rshtukaraxondevgroup.bluetoothtest.presenter;

import com.rshtukaraxondevgroup.bluetoothtest.repository.ReceivedPhotosRepository;
import com.rshtukaraxondevgroup.bluetoothtest.view.PhotosScreen;

public class ReceivedPhotoPresenter {
    private ReceivedPhotosRepository mReceivedPhotosRepository;
    private PhotosScreen mReceivedPhotosScreen;

    public ReceivedPhotoPresenter(PhotosScreen photosScreen, ReceivedPhotosRepository receivedPhotosRepository) {
        this.mReceivedPhotosScreen = photosScreen;
        this.mReceivedPhotosRepository = receivedPhotosRepository;
    }

    public void getReceivedPhotoList() {
        mReceivedPhotosRepository.getPhotosListFromDB()
                .subscribe(list -> mReceivedPhotosScreen.addPhotosList(list),
                        throwable -> mReceivedPhotosScreen.showError(throwable.getMessage()));
    }
}
