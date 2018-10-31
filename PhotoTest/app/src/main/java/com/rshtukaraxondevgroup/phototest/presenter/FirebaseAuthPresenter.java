package com.rshtukaraxondevgroup.phototest.presenter;

import android.util.Log;

import com.rshtukaraxondevgroup.phototest.repository.AuthRepositoryListener;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;
import com.rshtukaraxondevgroup.phototest.view.AuthScreen;

public class FirebaseAuthPresenter implements AuthRepositoryListener {
    private static final String TAG = FirebaseAuthPresenter.class.getCanonicalName();
    private FirebaseAuthRepository mFirebaseAuthRepository;
    private AuthScreen mAuthScreen;

    public FirebaseAuthPresenter(FirebaseAuthRepository firebaseAuthRepository, AuthScreen authScreen) {
        this.mFirebaseAuthRepository = firebaseAuthRepository;
        this.mAuthScreen = authScreen;
    }

    @Override
    public void showSuccessAuth() {
        mAuthScreen.showSuccessAuth();
    }

    @Override
    public void showErrorAuth(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        mAuthScreen.showErrorAuth();
    }

    @Override
    public void showSuccessRegistration() {
        mAuthScreen.showSuccessRegistration();
    }

    @Override
    public void showErrorRegistration(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        mAuthScreen.showErrorRegistration();
    }

    public boolean isCurrentUserExist() {
        return mFirebaseAuthRepository.isCurrentUserExist();
    }

    public void singIn(String email, String password) {
        mFirebaseAuthRepository.signIn(email, password, this);
    }

    public void registration(String email, String password) {
        mFirebaseAuthRepository.registration(email, password, this);
    }
}
