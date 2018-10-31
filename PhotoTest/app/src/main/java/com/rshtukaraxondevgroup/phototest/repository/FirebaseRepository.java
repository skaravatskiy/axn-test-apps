package com.rshtukaraxondevgroup.phototest.repository;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.Utils;
import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;


public class FirebaseRepository {
    private static final String TAG = FirebaseRepository.class.getCanonicalName();
    private StorageReference mStorageRef;

    public FirebaseRepository() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadFileInFirebaseStorage(String imageUri,
                                            InputStream inputStream,
                                            RepositoryListener listener) {
        UploadTask uploadTask = mStorageRef.child(Objects.requireNonNull(imageUri)).putStream(inputStream);
        uploadTask.addOnSuccessListener(taskSnapshot -> downloadFile(imageUri, listener));
    }

    private void downloadFile(String imageUri, RepositoryListener listener) {
        File localFile = null;
        try {
            localFile = Utils.getOutputMediaFile(Constants.FILE_NAME_FB_DOWNLOAD);
        } catch (CreateDirectoryException e) {
            listener.downloadError(e);
            Log.e(TAG, e.getMessage());
        }
        File finalLocalFile = localFile;
        mStorageRef.child(imageUri).getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> listener.downloadSuccessful(finalLocalFile))
                .addOnFailureListener(listener::downloadError);
    }
}
