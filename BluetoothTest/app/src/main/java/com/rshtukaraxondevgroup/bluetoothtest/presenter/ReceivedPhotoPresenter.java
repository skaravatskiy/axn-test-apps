package com.rshtukaraxondevgroup.bluetoothtest.presenter;

import com.rshtukaraxondevgroup.bluetoothtest.repository.ReceivedPhotosRepository;
import com.rshtukaraxondevgroup.bluetoothtest.view.ReceivedPhotosScreen;

public class ReceivedPhotoPresenter {
    private ReceivedPhotosRepository mReceivedPhotosRepository;
    private ReceivedPhotosScreen mReceivedPhotosScreen;

    public ReceivedPhotoPresenter(ReceivedPhotosScreen receivedPhotosScreen, ReceivedPhotosRepository receivedPhotosRepository) {
        this.mReceivedPhotosScreen = receivedPhotosScreen;
        this.mReceivedPhotosRepository = receivedPhotosRepository;
    }

    public void getReceivedPhotoList() {
        mReceivedPhotosRepository.getPhotosListFromDB()
                .subscribe(list -> mReceivedPhotosScreen.addBooksList(list),
                        throwable -> mReceivedPhotosScreen.showError(throwable));
    }
}
