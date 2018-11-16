package com.rshtukaraxondevgroup.maptest.repository;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface PlacesRepositoryListener extends BaseRepositoryListener {
    void downloadSuccessful(MarkerOptions markerOptions, LatLng latLng);
}
