package com.rshtukaraxondevgroup.maptest.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rshtukaraxondevgroup.maptest.repository.DirectionsMapRepository;
import com.rshtukaraxondevgroup.maptest.repository.DirectionsRepositoryListener;
import com.rshtukaraxondevgroup.maptest.view.MapsScreen;

public class DirectionsMapPresenter implements DirectionsRepositoryListener {
    private MapsScreen mMapsScreen;
    private DirectionsMapRepository mRepository;

    public DirectionsMapPresenter(MapsScreen mapsScreen, DirectionsMapRepository repository) {
        this.mMapsScreen = mapsScreen;
        this.mRepository = repository;
    }

    @Override
    public void downloadError() {
        mMapsScreen.showError();
    }

    @Override
    public void downloadSuccessful(PolylineOptions polylineOptions) {
        mMapsScreen.drawingPolyline(polylineOptions);
    }

    public void downloadMapDirection(LatLng origin, LatLng dest) {
        mRepository.download(origin, dest, this);
    }
}