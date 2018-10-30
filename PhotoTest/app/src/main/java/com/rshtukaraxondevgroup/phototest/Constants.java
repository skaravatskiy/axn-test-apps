package com.rshtukaraxondevgroup.phototest;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String ACCESS_DROPBOX_TOKEN = "rtPumCpS8cAAAAAAAAAACaQclsaKp0_SPILHPEaJcvucal5Q2rT24puVgn9nKEaM";
    public static final String CLIENT_IDENTIFIER = "dropbox/java-tutorial";
    public static final String CHILD_FILE_DIRECTORY = "PhotoDemo";
    public static final String FILE_CREATION_DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String FAILED_TO_CREATE_DIRECTORY = "failed to create directory";
    public static final String FILE_NAME = "IMG_";
    public static final String FILE_NAME_DB_DOWNLOAD = "IMG_DB_Download_";
    public static final String FILE_NAME_GD_DOWNLOAD = "IMG_GD_Download_";
    public static final String FILE_NAME_FB_DOWNLOAD = "IMG_GD_Download_";
    public static final String FILE_TYPE = "image/jpeg";
    public static final String FILE_FORMAT = ".jpg";
    public static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final String EXTRA_URI = "uri";
    public static final int REQUEST_SIGN_IN_FIREBASE = 100;
    public static final int REQUEST_SIGN_IN_GOOGLE_DRIVE = 200;
    public static final int TAKE_PICTURE = 1;
    public static final List<String> SCOPES;
    static {
        SCOPES = new ArrayList<>();
        SCOPES.add("https://www.googleapis.com/auth/drive");
    }
}
