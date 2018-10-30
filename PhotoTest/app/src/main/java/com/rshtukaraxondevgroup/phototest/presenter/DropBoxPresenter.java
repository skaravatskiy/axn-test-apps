package com.rshtukaraxondevgroup.phototest.presenter;

import android.net.Uri;
import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.DropBoxRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class DropBoxPresenter implements RepositoryListener {
    private static final String TAG = FirebasePresenter.class.getCanonicalName();
    private UploadScreen uploadScreen;
    private DropBoxRepository dropBoxRepository;

    public DropBoxPresenter(UploadScreen uploadScreen, DropBoxRepository dropBoxRepository) {
        this.uploadScreen = uploadScreen;
        this.dropBoxRepository = dropBoxRepository;
    }

    public void uploadDownloadFileFromDropBox(Uri mImageUri) {
        dropBoxRepository.uploadDownloadFile(mImageUri, this);
    }

    @Override
    public void downloadError(Throwable e) {
        uploadScreen.showError(e);
        Log.e(TAG, e.getMessage());
    }

    @Override
    public void downloadSuccessful(File file) {
        uploadScreen.showImage(file);
        Log.d(TAG, file.getName());
    }
}