package com.rshtukaraxondevgroup.logintest;

import com.arellomobile.mvp.MvpView;

public interface DetailsView extends MvpView {
    void initView(String login, String password);
}
