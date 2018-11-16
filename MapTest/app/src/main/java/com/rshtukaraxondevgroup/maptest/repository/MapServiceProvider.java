package com.rshtukaraxondevgroup.maptest.repository;


public class MapServiceProvider {

    private static IMapRepository repository;

    private MapServiceProvider() {
    }

    public static IMapRepository getInstance() {
        if (repository == null) {
            repository = new MapService();
        }
        return repository;
    }
}
