package com.rshtukaraxondevgroup.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class DetailsActivity extends MvpActivity implements DetailsView {
    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        byte[] mLogin = intent.getByteArrayExtra("LOGIN");
        byte[] mPassword = intent.getByteArrayExtra("PASSWORD");
        String key = intent.getStringExtra("KEY");

        TextView encodedTextView = findViewById(R.id.textViewEncoded);
        encodedTextView.setText("[ENCODED]:\n" + "login:" + "\n" +
                Base64.encodeToString(mLogin, Base64.DEFAULT) + "password:" + "\n" +
                Base64.encodeToString(mPassword, Base64.DEFAULT) + "key:" + "\n" +
                key + "\n");
        try {
            detailsPresenter.init(mLogin, mPassword, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView(String login, String password) {
        TextView decodedTextView = findViewById(R.id.textViewDecoded);
        decodedTextView.setText("[DECODED]:\n" + "login:\n" + login + "\n" + "password:\n" + password + "\n");
    }
}
