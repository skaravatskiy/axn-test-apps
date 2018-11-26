package com.rshtukaraxondevgroup.bluetoothtest.view;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

public interface ReceivedPhotosScreen {
    void addBooksList(List<PhotoModel> list);

    void showError(Throwable throwable);
}
