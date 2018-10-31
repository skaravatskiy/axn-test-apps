package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class GoogleDrivePresenter implements RepositoryListener {
    private static final String TAG = GoogleDrivePresenter.class.getCanonicalName();
    private UploadScreen mUploadScreen;
    private GoogleDriveRepository mGoogleDriveRepository;

    public GoogleDrivePresenter(UploadScreen uploadScreen, GoogleDriveRepository googleDriveRepository) {
        this.mUploadScreen = uploadScreen;
        this.mGoogleDriveRepository = googleDriveRepository;
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

    public void uploadDownloadFileToGoogleDrive(String imageUri, GoogleAccountCredential credentials) {
        mUploadScreen.showProgressBar();
        mGoogleDriveRepository.uploadFileInGoogleDrive(imageUri, credentials, this);
    }
}