package com.rshtukaraxondevgroup.phototest.repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.api.services.drive.Drive;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.rshtukaraxondevgroup.phototest.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoogleDriveRepository {
    private static final String TAG = GoogleDriveRepository.class.getCanonicalName();
    private List listeners = new ArrayList();
    private Context context;
    private GoogleAccountCredential credentials;

    public GoogleDriveRepository(Context context) {
        this.context = context;
    }

    public Intent buildGoogleAccountCredential() {
        credentials = GoogleAccountCredential.usingOAuth2(context, Constants.SCOPES);
        return credentials.newChooseAccountIntent();
    }

    public void uploadFileInGoogleDrive(Uri mImageUri, String accountName) {
        if (accountName != null && mImageUri != null) {
            credentials.setSelectedAccountName(accountName);

            HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
            Drive service = new Drive.Builder(HTTP_TRANSPORT, Constants.JSON_FACTORY, credentials)
                    .setApplicationName(Constants.APPLICATION_NAME)
                    .build();

            File filePath = new File(mImageUri.getPath());
            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(filePath.getName())
                    .setMimeType("image/jpeg");

            File downloadFile = getOutputMediaFile();

            Observable.fromCallable(() -> {
                com.google.api.services.drive.model.File fileUpload = null;
                try {
                    fileUpload = service.files().create(fileMetadata, mediaContent)
                            .setFields("id")
                            .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fileId = fileUpload.getId();
                try {
                    OutputStream outputStream = new FileOutputStream(downloadFile);
                    service.files().get(fileId)
                            .executeMediaAndDownloadTo(outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return downloadFile;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        RepositoryListener listener = (RepositoryListener) listeners.get(0);
                        listener.downloadSuccessful(result);
                    }, throwable -> {
                        RepositoryListener listener = (RepositoryListener) listeners.get(0);
                        listener.downloadError(throwable);
                    });

        }
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
                "IMG_GDownload_" + timeStamp + ".jpg");
    }

    public interface RepositoryListener {
        void downloadError(Throwable e);

        void downloadSuccessful(File file);
    }

    public void addListener(RepositoryListener listener) {
        listeners.add(listener);
    }
}
