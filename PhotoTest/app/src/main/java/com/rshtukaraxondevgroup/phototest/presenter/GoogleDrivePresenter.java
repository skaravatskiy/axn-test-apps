package com.rshtukaraxondevgroup.phototest.presenter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class GoogleDrivePresenter implements GoogleDriveRepository.RepositoryListener {
    private static final String TAG = GoogleDrivePresenter.class.getCanonicalName();
    private UploadScreen uploadScreen;
    private GoogleDriveRepository googleDriveRepository;

    public GoogleDrivePresenter(UploadScreen uploadScreen, GoogleDriveRepository googleDriveRepository) {
        this.uploadScreen = uploadScreen;
        this.googleDriveRepository = googleDriveRepository;
    }

    public Intent signIn() {
        return googleDriveRepository.buildGoogleAccountCredential();
    }

    public void uploadDownloadFileToGoogleDrive(Uri mImageUri, String accountName) {
        googleDriveRepository.uploadFileInGoogleDrive(mImageUri, accountName);
    }

    @Override
    public void downloadError(Throwable e) {
        uploadScreen.showError(e);
        Log.d(TAG, "" + e);
    }

    @Override
    public void downloadSuccessful(File file) {
        uploadScreen.showImage(file);
        Log.d(TAG, "" + file.getName());
    }
}
