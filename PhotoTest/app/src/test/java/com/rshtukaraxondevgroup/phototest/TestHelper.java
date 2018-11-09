package com.rshtukaraxondevgroup.phototest;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.File;
import java.io.InputStream;

public class TestHelper {
    public final static String uriString = "file:///storage/emulated/0/Pictures/PhotoDemo/sign-info-icon.png";
    public static InputStream inputStream;
    public final static File file = new File(uriString);
    public final static Throwable throwable = new Throwable();
    public final static GoogleAccountCredential credentials = null;
    public final static String email = "test@t.ua";
    public final static String password = "123456";
}
