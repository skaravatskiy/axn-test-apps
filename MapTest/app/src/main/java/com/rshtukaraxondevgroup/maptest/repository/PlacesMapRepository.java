package com.rshtukaraxondevgroup.maptest.repository;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.rshtukaraxondevgroup.maptest.Constants.KEY;
import static com.rshtukaraxondevgroup.maptest.Constants.PROXIMITY_RADIUS;

public class PlacesMapRepository {
    private static final String TAG = PlacesMapRepository.class.getCanonicalName();

    private MapApi mApi;

    public PlacesMapRepository() {
        MapService mapService = new MapService();
        this.mApi = mapService.getClient().create(MapApi.class);
    }

    public void download(double latitude, double longitude, String nearbyPlace, PlacesRepositoryListener listener) {
        String location = latitude + "," + longitude;

        mApi.getPlacesResults(location, PROXIMITY_RADIUS, nearbyPlace, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placesResults -> {
                    for (int i = 0; i < placesResults.getResults().size(); i++) {
                        MarkerOptions markerOptions = new MarkerOptions();

                        double lat = placesResults.getResults().get(i).getGeometry().getLocation().getLat();
                        double lng = placesResults.getResults().get(i).getGeometry().getLocation().getLng();
                        LatLng latLng = new LatLng(lat, lng);
                        markerOptions.position(latLng);
                        markerOptions.title(placesResults.getResults().get(i).getName());
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                        listener.downloadSuccessful(markerOptions, latLng);
                    }
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                    listener.downloadError();
                });
    }
}
