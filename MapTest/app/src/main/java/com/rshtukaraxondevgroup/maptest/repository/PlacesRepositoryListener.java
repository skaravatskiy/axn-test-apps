package com.rshtukaraxondevgroup.maptest.repository;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public interface PlacesRepositoryListener {
    void downloadError(Throwable e);

    void downloadSuccessful(MarkerOptions markerOptions, LatLng latLng);
}
