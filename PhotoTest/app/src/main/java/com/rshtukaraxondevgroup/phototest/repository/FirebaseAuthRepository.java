package com.rshtukaraxondevgroup.phototest.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rshtukaraxondevgroup.phototest.view.AuthActivity;

public class FirebaseAuthRepository {
    private FirebaseAuth mAuth;
    private boolean auth = false;
    private boolean registration = false;

    public FirebaseAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean currentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean signIn(String email, String password, AuthActivity activity) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                auth = true;
            }
        }).addOnFailureListener(activity, Throwable::printStackTrace);

        return auth;
    }

    public boolean registration(String email, String password, AuthActivity activity) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                registration = true;
            }
        }).addOnFailureListener(activity, Throwable::printStackTrace);
        return registration;
    }
}
