package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.AuthRepositoryListener;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;
import com.rshtukaraxondevgroup.phototest.view.AuthScreen;

public class FirebaseAuthPresenter implements AuthRepositoryListener {
    private static final String TAG = FirebaseAuthPresenter.class.getCanonicalName();
    private FirebaseAuthRepository firebaseAuthRepository;
    private AuthScreen authScreen;

    public FirebaseAuthPresenter(FirebaseAuthRepository firebaseAuthRepository, AuthScreen authScreen) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.authScreen = authScreen;
    }

    public boolean isCurrentUserExist() {
        return firebaseAuthRepository.isCurrentUserExist();
    }

    public void singIn(String email, String password) {
        firebaseAuthRepository.signIn(email, password, this);
    }

    public void registration(String email, String password) {
        firebaseAuthRepository.registration(email, password, this);
    }

    @Override
    public void showSuccessAuth() {
        authScreen.showSuccessAuth();
    }

    @Override
    public void showErrorAuth(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        authScreen.showErrorAuth();
    }

    @Override
    public void showSuccessRegistration() {
        authScreen.showSuccessRegistration();
    }

    @Override
    public void showErrorRegistration(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        authScreen.showErrorRegistration();
    }
}
