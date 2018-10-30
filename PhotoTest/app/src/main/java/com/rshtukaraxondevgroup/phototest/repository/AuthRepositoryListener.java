package com.rshtukaraxondevgroup.phototest.repository;

public interface AuthRepositoryListener {
    void showSuccessAuth();

    void showErrorAuth(Throwable throwable);

    void showSuccessRegistration();

    void showErrorRegistration(Throwable throwable);
}
