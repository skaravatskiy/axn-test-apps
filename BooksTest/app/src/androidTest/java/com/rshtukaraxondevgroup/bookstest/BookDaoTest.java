package com.rshtukaraxondevgroup.bookstest;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rshtukaraxondevgroup.bookstest.database.AppDatabase;
import com.rshtukaraxondevgroup.bookstest.database.BookDao;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BookDaoTest {
    private AppDatabase db;
    private BookDao bookDao;
    private List<BookModel> dbBooks = new ArrayList<>();

    @Before
    public void createDb() throws Exception {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();
        bookDao = db.bookDao();

    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void whenInsertBookThenReadTheSameOne() throws Exception {
        List<BookModel> books = TestHelperDB.createListOfBooks(1);
        bookDao.insert(books.get(0));
        bookDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    dbBooks = list;
                    assertEquals(1, dbBooks.size());
                    assertTrue(TestHelperDB.booksAreIdentical(books.get(0), dbBooks.get(0)));
                })
                .dispose();
    }

    @Test
    public void whenInsertEmployeesThenReadThem() throws Exception {
        List<BookModel> listOfBooks = TestHelperDB.createListOfBooks(5);
        bookDao.insertAll(listOfBooks);
        bookDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    dbBooks = list;
                    assertEquals(5, dbBooks.size());
                })
                .dispose();
    }

    @Test
    public void whenDeleteAllThenReadNothing() throws Exception {
        bookDao.deleteAll();
        List<BookModel> listOfBooks = TestHelperDB.createListOfBooks(5);
        bookDao.insertAll(listOfBooks);
        bookDao.deleteAll();
        bookDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    dbBooks = list;
                    assertTrue(dbBooks.isEmpty());
                })
                .dispose();
    }

}
