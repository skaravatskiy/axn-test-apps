package com.rshtukaraxondevgroup.phototest.repository;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.view.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DropBoxRepository {
    private static final String TAG = DropBoxRepository.class.getCanonicalName();
    private DbxClientV2 client;
    private List listeners = new ArrayList();

    public DropBoxRepository() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, Constants.ACCESS_DROPBOX_TOKEN);
    }

    public void uploadDownloadFile(Uri mImageUri) {
        File file = new File(mImageUri.getPath());
        File downloadFile = getOutputMediaFile();
        Observable.fromCallable(() -> {
            try (InputStream in = new FileInputStream(file)) {
                client.files().uploadBuilder(mImageUri.getPath()).uploadAndFinish(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (OutputStream outputStream = new FileOutputStream(downloadFile)) {
                client.files().downloadBuilder(mImageUri.getPath()).download(outputStream);
            } catch (Exception e) {
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
                "IMG_DBdownload_" + timeStamp + ".jpg");
    }

    public interface RepositoryListener {
        void downloadError(Throwable e);

        void downloadSuccessful(File file);
    }

    public void addListener(RepositoryListener listener) {
        listeners.add(listener);
    }

}
