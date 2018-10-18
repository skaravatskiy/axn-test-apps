package com.rshtukaraxondevgroup.bookstest.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.repository.BookRepository;
import com.rshtukaraxondevgroup.bookstest.view.MainScreen;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class BookPresenter extends MvpPresenter<MainScreen> {
    private static final String TAG = BookPresenter.class.getCanonicalName();
    private BookRepository bookRepository;

    public BookPresenter() {

    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void getBooksList(int page) {
        bookRepository.getBooksList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().addBooksList(list),
                        throwable -> getViewState().showError(throwable));
    }

    public void deleteItem(BookModel bookModel) {
        bookRepository.deleteItem(bookModel)
                .doOnError(throwable -> Log.d(TAG, throwable.getMessage()));
    }
}
