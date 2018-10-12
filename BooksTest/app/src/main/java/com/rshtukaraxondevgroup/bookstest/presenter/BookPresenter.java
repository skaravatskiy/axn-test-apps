package com.rshtukaraxondevgroup.bookstest.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.rshtukaraxondevgroup.bookstest.App;
import com.rshtukaraxondevgroup.bookstest.database.AppDatabase;
import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.repository.BookApi;
import com.rshtukaraxondevgroup.bookstest.repository.BookRepository;
import com.rshtukaraxondevgroup.bookstest.view.MainScreen;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class BookPresenter extends MvpPresenter<MainScreen> {
    private AppDatabase db = App.getInstance().getDatabase();
    private BookDao bookDao = db.bookDao();
    private BookRepository bookRepository = new BookRepository();
    private BookApi api = bookRepository.getClient().create(BookApi.class);

    public Single<List<BookModel>> getBooksList(int page) {
        return api.getBooks(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertToDB(List<BookModel> bookModels) {
        new Thread(() -> {
            for (BookModel bookModel : bookModels) {
                bookDao.getByUrl(bookModel.getUrl())
                        .subscribe(bookModelDB -> {
                            Log.d("getBooksList", "No insert");
                        }, throwable -> {
                            bookDao.insert(bookModel);
                            Log.d("getBooksList", "insert");
                        }).dispose();
            }
        }).start();
    }

    public Flowable<List<BookModel>> getBooksListFromDB() {
        return bookDao.getAll()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void deleteItem(BookModel bookModel) {
        new Thread(() -> {
            bookDao.delete(bookModel);
            bookDao.getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> getViewState().showBooksList(list));
        }).start();

    }
}
