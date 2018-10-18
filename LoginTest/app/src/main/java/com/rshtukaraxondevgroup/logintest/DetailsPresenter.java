package com.rshtukaraxondevgroup.logintest;

import android.util.Base64;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    void init(byte[] loginByte, byte[] passwordByte, String key) throws Exception {
        String login = decryptString(loginByte, stringToKey(key));
        String password = decryptString(passwordByte, stringToKey(key));

        getViewState().initView(login, password);
    }

    private static String decryptString(byte[] cipherText, SecretKey secret) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText));
    }

    private static SecretKey stringToKey(String stringKey) {
        byte[] encodedKey = Base64.decode(stringKey.trim(), Base64.DEFAULT);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
