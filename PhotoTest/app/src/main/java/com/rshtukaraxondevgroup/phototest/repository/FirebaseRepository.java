package com.rshtukaraxondevgroup.phototest.repository;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FirebaseRepository {
    private static final String TAG = FirebaseRepository.class.getCanonicalName();
    private StorageReference mStorageRef;

    public FirebaseRepository() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadFileInFirebaseStorage(String mImageUri,
                                            InputStream inputStream,
                                            File environmentFile,
                                            RepositoryListener listener) {
        UploadTask uploadTask = mStorageRef.child(Objects.requireNonNull(mImageUri)).putStream(inputStream);
        uploadTask.addOnSuccessListener(taskSnapshot -> downloadFile(mImageUri, environmentFile, listener));
    }

    private void downloadFile(String mImageUri, File environmentFile, RepositoryListener listener) {
        File localFile = null;
        try {
            localFile = getOutputMediaFile(environmentFile);
        } catch (CreateDirectoryException e) {
            listener.downloadError(e);
            Log.e(TAG, e.getMessage());
        }
        File finalLocalFile = localFile;
        mStorageRef.child(mImageUri).getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> listener.downloadSuccessful(finalLocalFile))
                .addOnFailureListener(listener::downloadError);
    }

    private static File getOutputMediaFile(File environmentFile) throws CreateDirectoryException {
        File mediaStorageDir = new File(environmentFile, Constants.CHILD_FILE_DIRECTORY);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                throw new CreateDirectoryException(Constants.FAILED_TO_CREATE_DIRECTORY);
            }
        }
        String timeStamp = new SimpleDateFormat(Constants.FILE_CREATION_DATE_FORMAT).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                Constants.FILE_NAME_FB_DOWNLOAD + timeStamp + Constants.FILE_FORMAT);
    }
}
