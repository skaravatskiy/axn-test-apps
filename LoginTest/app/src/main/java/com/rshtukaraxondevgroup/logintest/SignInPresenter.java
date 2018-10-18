package com.rshtukaraxondevgroup.logintest;

import android.util.Base64;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@InjectViewState
public class SignInPresenter extends MvpPresenter<SignInView> {

    void clickOnSignIn(String login, String password) throws Exception {
        SecretKey secretKey = generateKey();

        getViewState().signInSuccessfully(encryptString(login, secretKey),
                encryptString(password, secretKey),
                keyToString(secretKey));
    }

    private static byte[] encryptString(String message, SecretKey secret) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    private static String keyToString(SecretKey secretKey) {
        return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
    }
}
