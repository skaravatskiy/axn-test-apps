package com.rshtukaraxondevgroup.bookstest.repository;

import android.content.Context;

public class RepositoryProvider {
    private static BookRepository repository;

    private RepositoryProvider() {
    }

    public static BookRepository getInstance(Context context) {
        if (repository == null) {
            repository = new BookRepository(context);
        }
        return repository;
    }
}
