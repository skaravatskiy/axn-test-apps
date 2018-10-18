package com.rshtukaraxondevgroup.logintest;

import com.arellomobile.mvp.MvpView;

public interface SignInView extends MvpView {
    void signInSuccessfully(byte[] login, byte[] password, String key);
}
