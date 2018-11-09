package com.rshtukaraxondevgroup.phototest.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository {
    private FirebaseAuth mAuth;

    public FirebaseAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean isCurrentUserExist() {
        return mAuth.getCurrentUser() != null;
    }

    public void signIn(String email, String password, AuthRepositoryListener authRepositoryListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        authRepositoryListener.showSuccessAuth();
                    }
                })
                .addOnFailureListener(e -> authRepositoryListener.showErrorAuth(e));
    }

    public void registration(String email, String password, RegistrationRepositoryListener registrationRepositoryListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationRepositoryListener.showSuccessRegistration();
                    }
                })
                .addOnFailureListener(e -> registrationRepositoryListener.showErrorRegistration(e));
    }
}