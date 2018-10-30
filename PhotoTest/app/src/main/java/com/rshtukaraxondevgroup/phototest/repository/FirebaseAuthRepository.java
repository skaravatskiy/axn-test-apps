package com.rshtukaraxondevgroup.phototest.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository {
    private FirebaseAuth mAuth;

    public FirebaseAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean currentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

    public void signIn(String email, String password, AuthRepositoryListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showSuccessAuth();
                    }
                })
                .addOnFailureListener(e -> listener.showErrorAuth(e));
    }

    public void registration(String email, String password, AuthRepositoryListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showSuccessRegistration();
                    }
                })
                .addOnFailureListener(e -> listener.showErrorRegistration(e));
    }
}