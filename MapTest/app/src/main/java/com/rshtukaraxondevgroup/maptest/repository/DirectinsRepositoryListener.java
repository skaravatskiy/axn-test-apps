package com.rshtukaraxondevgroup.maptest.repository;

import com.google.android.gms.maps.model.PolylineOptions;

public interface DirectinsRepositoryListener {
    void downloadError(Throwable e);

    void downloadSuccessful(PolylineOptions polylineOptions);
}
