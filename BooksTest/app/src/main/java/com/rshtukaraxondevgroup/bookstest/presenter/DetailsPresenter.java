package com.rshtukaraxondevgroup.bookstest.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.rshtukaraxondevgroup.bookstest.App;
import com.rshtukaraxondevgroup.bookstest.database.AppDatabase;
import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.view.DetailsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    public void init(String bookUrl) {
        AppDatabase db = App.getInstance().getDatabase();
        BookDao bookDao = db.bookDao();

        bookDao.getByUrl(bookUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookModel -> {
                    Log.d("getBooksList", bookModel.getNumberOfPages().toString());
                    getViewState().initView(bookModel);
                });
    }
}