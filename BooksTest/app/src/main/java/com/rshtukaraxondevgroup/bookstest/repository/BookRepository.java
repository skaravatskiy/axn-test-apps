package com.rshtukaraxondevgroup.bookstest.repository;

import android.util.Log;

import com.rshtukaraxondevgroup.bookstest.App;
import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookRepository {
    private static final String TAG = BookRepository.class.getCanonicalName();
    private BookDao bookDao;
    private BookApi api;
    private NetworkManager networkManager;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public BookRepository(NetworkManager networkManager) {
        BookService bookService = new BookService();
        this.api = bookService.getClient().create(BookApi.class);
        this.bookDao = App.getDatabase().bookDao();
        this.networkManager = networkManager;
    }

    public Single<List<BookModel>> getBooksList(int page) {
        if (networkManager.isNetworkAvailable()) {
            return api.getBooks(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(list -> {
                        insertToDB(list);
                    });
        } else if (page == 1) {
            return getBooksListFromDB();
        } else {
            return Single.just(new ArrayList<>());
        }
    }

    private void insertToDB(List<BookModel> bookModels) {
        executorService.execute(() -> {
            for (BookModel bookModel : bookModels) {
                bookDao.getByUrl(bookModel.getUrl())
                        .subscribe(bookModelDB -> {
                            Log.d(TAG, "item No insert");
                        }, throwable -> {
                            bookDao.insert(bookModel);
                            Log.d(TAG, "item insert");
                        });
            }
        });
    }

    private Single<List<BookModel>> getBooksListFromDB() {
        return bookDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(list -> bookDao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
    }

    public Single<BookModel> getBookDetails(String bookUrl) {
        return bookDao.getByUrl(bookUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<BookModel>> deleteItem(BookModel bookModel) {
        executorService.execute(() -> {
            bookDao.delete(bookModel);
        });
        return bookDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
