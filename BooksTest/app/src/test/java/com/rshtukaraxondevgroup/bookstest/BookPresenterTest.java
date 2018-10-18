package com.rshtukaraxondevgroup.bookstest;

import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;
import com.rshtukaraxondevgroup.bookstest.repository.BookRepository;
import com.rshtukaraxondevgroup.bookstest.view.MainScreen$$State;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BookPresenterTest {
    static final List<BookModel> LIST = TestHelper.createListOfBooks(10);
    static final BookModel BOOK_MODEL = TestHelper.createBookModel();

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    MainScreen$$State mainScreen;
    @Mock
    BookRepository bookRepository;
    private BookPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new BookPresenter();
        presenter.setBookRepository(bookRepository);
        presenter.setViewState(mainScreen);

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void getBooksList() {
        when(bookRepository.getBooksList(1)).thenReturn(TestHelper.createSingleListOfBooks(10));
        presenter.getBooksList(1);
        verify(mainScreen).addBooksList(LIST);
    }

    @Test
    public void getBooksListFail() {
        Throwable throwable = new IOException();
        when(bookRepository.getBooksList(1)).thenReturn(Single.error(throwable));
        presenter.getBooksList(1);
        verify(mainScreen).showError(throwable);
    }

    @Test
    public void deleteItem() {
        when(bookRepository.deleteItem(BOOK_MODEL)).thenReturn(TestHelper.createSingleListOfBooks(10));
        presenter.deleteItem(BOOK_MODEL);
        verify(mainScreen).setBookList(LIST);
    }

}
