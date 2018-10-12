package com.rshtukaraxondevgroup.bookstest.view;

import com.arellomobile.mvp.MvpView;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

public interface DetailsView extends MvpView {
    void initView(BookModel bookModel);
}
