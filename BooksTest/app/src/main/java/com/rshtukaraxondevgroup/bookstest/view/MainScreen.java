package com.rshtukaraxondevgroup.bookstest.view;

import com.arellomobile.mvp.MvpView;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.List;

public interface MainScreen extends MvpView {
    void addBooksList(List<BookModel> list);
    void showError(Throwable throwable);
    void onItemSelected(BookModel bookModel);
    void onLongClick(BookModel bookModel);
}
