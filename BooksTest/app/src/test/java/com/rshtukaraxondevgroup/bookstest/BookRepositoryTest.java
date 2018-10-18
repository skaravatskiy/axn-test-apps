package com.rshtukaraxondevgroup.bookstest;

import android.content.Context;
import android.util.Log;

import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;
import com.rshtukaraxondevgroup.bookstest.repository.BookApi;
import com.rshtukaraxondevgroup.bookstest.repository.BookRepository;
import com.rshtukaraxondevgroup.bookstest.repository.NetworkManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BookRepositoryTest {
    private static final String TAG = BookPresenter.class.getCanonicalName();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    NetworkManager networkManager;
    @Mock
    BookApi bookApi;
    @Mock
    BookDao bookDao;

    private BookRepository bookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookRepository = new BookRepository(networkManager);

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void getBooksList() {
        executorService.execute(() -> {
        when(bookApi.getBooks(1)).thenReturn(TestHelper.createSingleListOfBooks(10));
        bookRepository.getBooksList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> Assert.assertEquals(list, TestHelper.createSingleListOfBooks(1)))
                .dispose();
        });
    }

    @Test
    public void getBooksListByUrl() {
        executorService.execute(() -> {
            bookDao.insert(TestHelper.createBookModel());
            when(bookDao.getByUrl("url")).thenReturn(TestHelper.createSingleBookModel());
            bookRepository.getBookDetails("url")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bookModel -> {
                                Assert.assertEquals(bookModel, TestHelper.createBookModel());
                            },
                            throwable -> Log.d(TAG, throwable.getMessage()))
                    .dispose();
        });
    }

    @Test
    public void deleteItem() {
        executorService.execute(() -> {
            bookDao.delete(TestHelper.createBookModel());
            when(bookDao.getAll()).thenReturn(TestHelper.createSingleListOfBooks(1));
            bookRepository.deleteItem(TestHelper.createBookModel())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> Assert.assertEquals(list, TestHelper.createListOfBooks(1)))
                    .dispose();
        });
    }
}
