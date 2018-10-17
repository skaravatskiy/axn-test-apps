package com.rshtukaraxondevgroup.bookstest;

import android.content.Context;
import android.util.Log;

import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.repository.BookApi;
import com.rshtukaraxondevgroup.bookstest.repository.BookRepository;

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
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    Context context;
    @Mock
    BookApi bookApi;
    @Mock
    BookDao bookDao;

    private BookRepository bookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookRepository = new BookRepository(context);

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void getBooksList() {
//        when(bookApi.getBooks(1)).thenReturn(TestHelper.createSingleListOfBooks(10));
//        bookRepository.getBooksList(1);
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
                            throwable -> Log.d("getBookDetails", throwable.getMessage()))
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
