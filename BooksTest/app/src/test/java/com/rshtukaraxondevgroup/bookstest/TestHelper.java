package com.rshtukaraxondevgroup.bookstest;

import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class TestHelper {
    public static List<BookModel> createListOfBooks(int i) {
        List<BookModel> bookModels = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        int j;
        for (j = 0; j < i; j++) {
            BookModel model = new BookModel("url" + j, "Book name" + j, "400", strings, 5, "publ",
                    "country", "type", "released", strings, strings);
            bookModels.add(model);
        }
        return bookModels;
    }

    public static Single<List<BookModel>> createSingleListOfBooks(int i) {
        List<BookModel> bookModels = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        int j;
        for (j = 0; j < i; j++) {
            BookModel model = new BookModel("url" + j, "Book name" + j, "400", strings, 5, "publ",
                    "country", "type", "released", strings, strings);
            bookModels.add(model);
        }
        return Single.just(bookModels);
    }

    public static BookModel createBookModel () {
        List<String> strings = new ArrayList<>();
        BookModel model = new BookModel("url", "Book name", "400", strings, 5, "publ",
                "country", "type", "released", strings, strings);
        return model;
    }

    public static Single<BookModel> createSingleBookModel () {
        List<String> strings = new ArrayList<>();
        BookModel model = new BookModel("url", "Book name", "400", strings, 5, "publ",
                "country", "type", "released", strings, strings);
        return Single.just(model);
    }
}
