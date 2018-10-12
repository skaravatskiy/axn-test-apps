package com.rshtukaraxondevgroup.logintest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class SignInActivity extends MvpActivity implements SignInView {
    @InjectPresenter
    SignInPresenter signInPresenter;

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;
    private FrameLayout mButtonSignIn;

    private String mLogin;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEditTextLogin = findViewById(R.id.edit_text_login);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mButtonSignIn = findViewById(R.id.button_sign_in);

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }
                mLogin = mEditTextLogin.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                try {
                    signInPresenter.clickOnSignIn(mLogin, mPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(mEditTextLogin.getText().toString().trim())) {
            mEditTextLogin.setError("Required");
            result = false;
        } else {
            mEditTextLogin.setError(null);
        }
        if (TextUtils.isEmpty(mEditTextPassword.getText().toString().trim())) {
            mEditTextPassword.setError("Required");
            result = false;
        }
        else {
            mEditTextPassword.setError(null);
        }
        return result;
    }

    @Override
    public void signInSuccessfully(byte[] login, byte[] password, String key) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("KEY", key);
        startActivity(intent);
    }
}
