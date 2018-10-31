package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;
import java.io.InputStream;

public class FirebasePresenter implements RepositoryListener {
    private static final String TAG = FirebasePresenter.class.getCanonicalName();
    private UploadScreen mUploadScreen;
    private FirebaseRepository mFirebaseRepository;

    public FirebasePresenter(UploadScreen uploadScreen, FirebaseRepository firebaseRepository) {
        this.mUploadScreen = uploadScreen;
        this.mFirebaseRepository = firebaseRepository;
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

    public void uploadDownloadFileFromFirebase(String imageUri, InputStream stream) {
        mUploadScreen.showProgressBar();
        mFirebaseRepository.uploadFileInFirebaseStorage(imageUri, stream, this);
    }
}
