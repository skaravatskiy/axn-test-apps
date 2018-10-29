package com.rshtukaraxondevgroup.phototest.presenter;

import android.net.Uri;
import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

import java.io.File;

public class FirebasePresenter implements FirebaseRepository.RepositoryListener {
    private static final String TAG = FirebasePresenter.class.getCanonicalName();
    private UploadScreen uploadScreen;
    private FirebaseRepository firebaseRepository;

    public FirebasePresenter(UploadScreen uploadScreen, FirebaseRepository firebaseRepository) {
        this.uploadScreen = uploadScreen;
        this.firebaseRepository = firebaseRepository;
    }

    public void uploadDownloadFileFromFirebase(Uri mImageUri) {
        firebaseRepository.uploadFileInFirebaseStorage(mImageUri);
    }

    @Override
    public void downloadError(Exception e) {
        uploadScreen.showError(e);
        Log.d(TAG, "" + e);
    }

    @Override
    public void downloadSuccessful(File file) {
        uploadScreen.showImage(file);
        Log.d(TAG, "" + file.getName());
    }
}
