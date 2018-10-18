package com.rshtukaraxondevgroup.bookstest;

import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class TestHelperDB {
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

    public static boolean booksAreIdentical(BookModel bookModel, BookModel bookModel1) {
        if (bookModel.equals(bookModel1)) {
            return true;
        } else {
            return false;
        }
    }
}
