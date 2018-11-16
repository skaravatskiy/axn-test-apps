package com.rshtukaraxondevgroup.maptest.repository;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rshtukaraxondevgroup.maptest.model.directions.Leg;
import com.rshtukaraxondevgroup.maptest.model.directions.Route;
import com.rshtukaraxondevgroup.maptest.model.directions.Step;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.rshtukaraxondevgroup.maptest.Constants.DIRECTIONS_MODE;
import static com.rshtukaraxondevgroup.maptest.Constants.KEY;
import static com.rshtukaraxondevgroup.maptest.Utils.decodePoly;

public class DirectionsMapRepository {
    private static final String TAG = DirectionsMapRepository.class.getCanonicalName();

    private MapApi mApi;

    public DirectionsMapRepository() {
        MapService mapService = new MapService();
        this.mApi = mapService.getClient().create(MapApi.class);
    }

    public void download(LatLng originLatLng, LatLng destLatLng, DirectionsRepositoryListener listener) {
        String origin = originLatLng.latitude + "," + originLatLng.longitude;
        String dest = destLatLng.latitude + "," + destLatLng.longitude;

        mApi.getDirectionsResults(origin, dest, DIRECTIONS_MODE, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(directionsResult -> {
                            List<Route> routes = directionsResult.getRoutes();
                            ArrayList points = new ArrayList();
                            PolylineOptions lineOptions = new PolylineOptions();

                            for (int i = 0; i < routes.size(); i++) {
                                List<Leg> leg = routes.get(i).getLegs();
                                for (int j = 0; j < leg.size(); j++) {
                                    List<Step> steps = leg.get(j).getSteps();
                                    for (int k = 0; k < steps.size(); k++) {
                                        String polyline = steps.get(k).getPolyline().getPoints();
                                        List list = decodePoly(polyline);

                                        for (int l = 0; l < list.size(); l++) {
                                            double lat = ((LatLng) list.get(l)).latitude;
                                            double lng = ((LatLng) list.get(l)).longitude;
                                            LatLng position = new LatLng(lat, lng);
                                            points.add(position);
                                        }
                                    }
                                }
                                lineOptions.addAll(points);
                                lineOptions.width(12);
                                lineOptions.color(Color.RED);
                                lineOptions.geodesic(true);
                            }
                            listener.downloadSuccessful(lineOptions);
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            listener.downloadError();
                        });
    }
}
