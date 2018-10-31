package com.rshtukaraxondevgroup.phototest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rshtukaraxondevgroup.phototest.R;
import com.rshtukaraxondevgroup.phototest.presenter.FirebaseAuthPresenter;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;


public class AuthActivity extends AppCompatActivity implements AuthScreen {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button mButtonLogin;
    private Button mButtonRegisration;
    private FirebaseAuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);

        FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository();
        authPresenter = new FirebaseAuthPresenter(firebaseAuthRepository, this);

        mButtonLogin = findViewById(R.id.btn_sign_in);
        mButtonRegisration = findViewById(R.id.btn_registration);
        findViewById(R.id.btn_sign_in).setOnClickListener(v -> signIn());
        findViewById(R.id.btn_registration).setOnClickListener(v -> registration());

        if (authPresenter.isCurrentUserExist()) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void signIn() {
        if (!validate()) {
            return;
        }
        authPresenter.singIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    private void registration() {
        if (!validate()) {
            return;
        }
        authPresenter.registration(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    private boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            editTextEmail.setError(getString(R.string.required));
            result = false;
        } else {
            editTextEmail.setError(null);
        }
        if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            editTextPassword.setError(getString(R.string.required));
            result = false;
        } else {
            editTextPassword.setError(null);
        }
        return result;
    }

    @Override
    public void showSuccessAuth() {
        Toast.makeText(AuthActivity.this, "Authorization successful", Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void showErrorAuth() {
        Toast.makeText(AuthActivity.this, "Authorization failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessRegistration() {
        Toast.makeText(AuthActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void showErrorRegistration() {
        Toast.makeText(AuthActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
    }
}