package com.rshtukaraxondevgroup.phototest.view;

import java.io.File;

public interface UploadScreen {
    void showImage(File file);

    void showError(Throwable throwable);
}
