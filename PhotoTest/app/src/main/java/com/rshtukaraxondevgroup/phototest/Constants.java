package com.rshtukaraxondevgroup.phototest;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String ACCESS_DROPBOX_TOKEN = "rtPumCpS8cAAAAAAAAAACaQclsaKp0_SPILHPEaJcvucal5Q2rT24puVgn9nKEaM";
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
