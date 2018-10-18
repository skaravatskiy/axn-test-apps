package com.rshtukaraxondevgroup.bookstest.repository;

public class RepositoryProvider {
    private static BookRepository repository;

    private RepositoryProvider() {
    }

    public static BookRepository getInstance(NetworkManager networkManager) {
        if (repository == null) {
            repository = new BookRepository(networkManager);
        }
        return repository;
    }
}
