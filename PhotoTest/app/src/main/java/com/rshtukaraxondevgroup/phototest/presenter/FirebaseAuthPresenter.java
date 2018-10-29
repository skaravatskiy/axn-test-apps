package com.rshtukaraxondevgroup.phototest.presenter;

import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;
import com.rshtukaraxondevgroup.phototest.view.AuthActivity;
import com.rshtukaraxondevgroup.phototest.view.AuthScreen;

public class FirebaseAuthPresenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private AuthScreen authScreen;

    public FirebaseAuthPresenter(FirebaseAuthRepository firebaseAuthRepository, AuthScreen authScreen) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.authScreen = authScreen;
    }

    public boolean getCurrentUser() {
        return firebaseAuthRepository.currentUser();
    }

    public void singIn(String email, String password, AuthActivity authActivity) {
        if (firebaseAuthRepository.signIn(email, password, authActivity)) {
            authScreen.showSuccessAuth();
        } else {
            authScreen.showErrorAuth();
        }
    }

    public void registration(String email, String password, AuthActivity authActivity) {
        if (firebaseAuthRepository.registration(email, password, authActivity)) {
            authScreen.showSuccessRegistration();
        } else {
            authScreen.showErrorRegistration();
        }
    }
}
