package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.DropBoxRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class DropBoxPresenter implements RepositoryListener {
    private static final String TAG = FirebasePresenter.class.getCanonicalName();
    private UploadScreen mUploadScreen;
    private DropBoxRepository mDropBoxRepository;

    public DropBoxPresenter(UploadScreen uploadScreen, DropBoxRepository dropBoxRepository) {
        this.mUploadScreen = uploadScreen;
        this.mDropBoxRepository = dropBoxRepository;
    }

    @Override
    public void downloadError(Throwable e) {
        mUploadScreen.hideProgressBar();
        mUploadScreen.showError(e);
        Log.e(TAG, e.getMessage());
    }

    @Override
    public void downloadSuccessful(File file) {
        mUploadScreen.hideProgressBar();
        mUploadScreen.showImage(file);
        Log.d(TAG, file.getName());
    }

    public void uploadDownloadFileFromDropBox(String imageUri) {
        mDropBoxRepository.uploadDownloadFile(imageUri, this);
        mUploadScreen.showProgressBar();
    }
}