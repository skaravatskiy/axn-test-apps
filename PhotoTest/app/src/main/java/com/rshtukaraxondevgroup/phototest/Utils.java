package com.rshtukaraxondevgroup.phototest;

import android.os.Environment;

import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static File getOutputMediaFile(String fileName) throws CreateDirectoryException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.CHILD_FILE_DIRECTORY);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                throw new CreateDirectoryException(Constants.FAILED_TO_CREATE_DIRECTORY);
            }
        }
        String timeStamp = new SimpleDateFormat(Constants.FILE_CREATION_DATE_FORMAT).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                fileName + timeStamp + Constants.FILE_FORMAT);
    }
}
