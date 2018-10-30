package com.rshtukaraxondevgroup.phototest.presenter;

import android.net.Uri;
import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;
import com.rshtukaraxondevgroup.phototest.repository.RepositoryListener;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class FirebasePresenter implements RepositoryListener {
    private static final String TAG = FirebasePresenter.class.getCanonicalName();
    private UploadScreen uploadScreen;
    private FirebaseRepository firebaseRepository;

    public FirebasePresenter(UploadScreen uploadScreen, FirebaseRepository firebaseRepository) {
        this.uploadScreen = uploadScreen;
        this.firebaseRepository = firebaseRepository;
    }

    public void uploadDownloadFileFromFirebase(Uri mImageUri) {
        firebaseRepository.uploadFileInFirebaseStorage(mImageUri, this);
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
