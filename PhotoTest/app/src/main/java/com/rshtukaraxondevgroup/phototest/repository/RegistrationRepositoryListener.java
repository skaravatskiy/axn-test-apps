package com.rshtukaraxondevgroup.phototest.repository;

public interface RegistrationRepositoryListener {
    void showSuccessRegistration();

    void showErrorRegistration(Throwable throwable);
}
