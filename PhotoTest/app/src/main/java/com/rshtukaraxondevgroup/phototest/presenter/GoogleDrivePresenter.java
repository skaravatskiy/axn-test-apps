package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class GoogleDrivePresenter implements RepositoryListener {
    private static final String TAG = GoogleDrivePresenter.class.getCanonicalName();
    private UploadScreen uploadScreen;
    private GoogleDriveRepository googleDriveRepository;

    public GoogleDrivePresenter(UploadScreen uploadScreen, GoogleDriveRepository googleDriveRepository) {
        this.uploadScreen = uploadScreen;
        this.googleDriveRepository = googleDriveRepository;
    }

    public void uploadDownloadFileToGoogleDrive(String mImageUri, GoogleAccountCredential credentials, File environmentFile) {
        googleDriveRepository.uploadFileInGoogleDrive(mImageUri, credentials, environmentFile, this);
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