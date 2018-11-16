package com.rshtukaraxondevgroup.maptest.repository;

import com.google.android.gms.maps.model.PolylineOptions;

public interface DirectionsRepositoryListener extends BaseRepositoryListener {
    void downloadSuccessful(PolylineOptions polylineOptions);
}
