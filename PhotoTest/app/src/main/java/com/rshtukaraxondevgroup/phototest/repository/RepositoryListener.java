package com.rshtukaraxondevgroup.phototest.repository;

import java.io.File;

public interface RepositoryListener {
    void downloadError(Throwable e);

    void downloadSuccessful(File file);
}
