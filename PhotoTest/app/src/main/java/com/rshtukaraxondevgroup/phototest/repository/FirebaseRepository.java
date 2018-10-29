package com.rshtukaraxondevgroup.phototest.repository;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rshtukaraxondevgroup.phototest.view.MainActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class FirebaseRepository {
    private static final String TAG = FirebaseRepository.class.getCanonicalName();
    private StorageReference mStorageRef;
    private File localFile = null;
    private List listeners = new ArrayList();

    public FirebaseRepository() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadFileInFirebaseStorage(Uri mImageUri) {
        UploadTask uploadTask = mStorageRef.child(Objects.requireNonNull(mImageUri.getPath())).putFile(mImageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> downloadFile(mImageUri));
    }

    private void downloadFile(Uri mImageUri) {
        localFile = getOutputMediaFile();
        mStorageRef.child(Objects.requireNonNull(mImageUri.getPath())).getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    RepositoryListener listener = (RepositoryListener) listeners.get(0);
                    listener.downloadSuccessful(localFile);
                })
                .addOnFailureListener(e -> {
                    RepositoryListener listener = (RepositoryListener) listeners.get(0);
                    listener.downloadError(e);
                });
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PhotoDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_FDownload_" + timeStamp + ".jpg");
    }

    public interface RepositoryListener {
        void downloadError(Exception e);

        void downloadSuccessful(File file);
    }

    public void addListener(RepositoryListener listener) {
        listeners.add(listener);
    }
}
