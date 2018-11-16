package com.rshtukaraxondevgroup.maptest.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public interface MapsScreen {
    void drawingPolyline(PolylineOptions polylineOptions);

    void drawingPlaces(MarkerOptions markerOptions, LatLng latLng);

    void showError();
}