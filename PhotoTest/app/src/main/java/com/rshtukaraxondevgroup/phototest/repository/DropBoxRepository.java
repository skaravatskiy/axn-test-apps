package com.rshtukaraxondevgroup.phototest.repository;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DropBoxRepository {
    private static final String TAG = DropBoxRepository.class.getCanonicalName();
    private DbxClientV2 client;

    public DropBoxRepository() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(Constants.CLIENT_IDENTIFIER).build();
        client = new DbxClientV2(config, Constants.ACCESS_DROPBOX_TOKEN);
    }

    public void uploadDownloadFile(Uri mImageUri, RepositoryListener listener) {
        File file = new File(mImageUri.getPath());
        File downloadFile = null;
        try {
            downloadFile = getOutputMediaFile();
        } catch (CreateDirectoryException e) {
            listener.downloadError(e);
            Log.d(TAG, e.getMessage());
        }
        File finalDownloadFile = downloadFile;
        Observable.fromCallable(() -> {
            try (InputStream in = new FileInputStream(file)) {
                client.files().uploadBuilder(mImageUri.getPath()).uploadAndFinish(in);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            try (OutputStream outputStream = new FileOutputStream(finalDownloadFile)) {
                client.files().downloadBuilder(mImageUri.getPath()).download(outputStream);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return finalDownloadFile;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> listener.downloadSuccessful(result),
                        throwable -> listener.downloadError(throwable));
    }

    private static File getOutputMediaFile() throws CreateDirectoryException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.CHILD_FILE_DIRECTORY);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                throw new CreateDirectoryException(Constants.FAILED_TO_CREATE_DIRECTORY);
            }
        }
        String timeStamp = new SimpleDateFormat(Constants.FILE_CREATION_DATE_FORMAT).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                Constants.FILE_NAME_DB_DOWNLOAD + timeStamp + Constants.FILE_FORMAT);
    }
}
