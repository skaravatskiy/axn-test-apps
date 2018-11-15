package com.rshtukaraxondevgroup.maptest.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rshtukaraxondevgroup.maptest.repository.PlacesMapRepository;
import com.rshtukaraxondevgroup.maptest.repository.PlacesRepositoryListener;
import com.rshtukaraxondevgroup.maptest.view.MapsScreen;

public class PlacesMapPresenter implements PlacesRepositoryListener {
    private MapsScreen mMapsScreen;
    private PlacesMapRepository mRepository;

    public PlacesMapPresenter(MapsScreen mapsScreen, PlacesMapRepository repository) {
        this.mMapsScreen = mapsScreen;
        this.mRepository = repository;
    }

    @Override
    public void downloadError(Throwable e) {
        mMapsScreen.showError(e);
    }

    @Override
    public void downloadSuccessful(MarkerOptions markerOptions, LatLng latLng) {
        mMapsScreen.drawingPlaces(markerOptions, latLng);
    }

    public void downloadMapPlaces(double latitude, double longitude, String nearbyPlace) {
        mRepository.download(latitude, longitude, nearbyPlace, this);
    }
}