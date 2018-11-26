package com.rshtukaraxondevgroup.bluetoothtest.view;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

public interface PhotosScreen {
    void addBooksList(List<PhotoModel> list);

    void showError(Throwable throwable);
}
