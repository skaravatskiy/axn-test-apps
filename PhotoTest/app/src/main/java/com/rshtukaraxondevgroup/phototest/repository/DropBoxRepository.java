package com.rshtukaraxondevgroup.phototest.repository;

import android.util.Log;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.Utils;
import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DropBoxRepository {
    private static final String TAG = DropBoxRepository.class.getCanonicalName();
    private DbxClientV2 mClient;

    public DropBoxRepository() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(Constants.CLIENT_IDENTIFIER).build();
        mClient = new DbxClientV2(config, Constants.ACCESS_DROPBOX_TOKEN);
    }

    public void uploadDownloadFile(String imageUri,
                                   RepositoryListener listener) {
        File file = new File(imageUri);
        File downloadFile = null;
        try {
            downloadFile = Utils.getOutputMediaFile(Constants.FILE_NAME_DB_DOWNLOAD);
        } catch (CreateDirectoryException e) {
            listener.downloadError(e);
            Log.d(TAG, e.getMessage());
        }
        File finalDownloadFile = downloadFile;
        Observable.fromCallable(() -> {
            try (InputStream in = new FileInputStream(file)) {
                mClient.files().uploadBuilder(imageUri).uploadAndFinish(in);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            try (OutputStream outputStream = new FileOutputStream(finalDownloadFile)) {
                mClient.files().downloadBuilder(imageUri).download(outputStream);
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
}
