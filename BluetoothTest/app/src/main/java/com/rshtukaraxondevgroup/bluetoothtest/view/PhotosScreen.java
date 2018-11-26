package com.rshtukaraxondevgroup.bluetoothtest.view;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

public interface PhotosScreen {
    void addPhotosList(List<PhotoModel> list);

    void showError(String throwable);
}
