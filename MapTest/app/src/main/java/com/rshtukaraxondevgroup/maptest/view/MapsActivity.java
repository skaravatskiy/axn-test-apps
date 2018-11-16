package com.rshtukaraxondevgroup.maptest.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rshtukaraxondevgroup.maptest.presenter.DirectionsMapPresenter;
import com.rshtukaraxondevgroup.maptest.R;
import com.rshtukaraxondevgroup.maptest.presenter.PlacesMapPresenter;
import com.rshtukaraxondevgroup.maptest.repository.DirectionsMapRepository;
import com.rshtukaraxondevgroup.maptest.repository.PlacesMapRepository;

import static com.rshtukaraxondevgroup.maptest.Constants.DEFAULT_LATITUDE;
import static com.rshtukaraxondevgroup.maptest.Constants.DEFAULT_LONGTITUDE;
import static com.rshtukaraxondevgroup.maptest.Constants.MY_PERMISSION_ACCESS_COURSE_LOCATION;

public class MapsActivity extends FragmentActivity implements MapsScreen, OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getCanonicalName();

    private Button mButtonDirection;
    private BottomNavigationView mBottomNavigationView;
    private DirectionsMapPresenter mDirectionsMapPresenter;
    private PlacesMapPresenter mPlacesMapPresenter;
    private Polyline mPolylineFinal;
    private GoogleMap mMap;
    private PlaceAutocompleteFragment mPlaceAutoComplete;
    private Marker mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mButtonDirection = findViewById(R.id.btn_direction);
        mBottomNavigationView = findViewById(R.id.btn_navigation_view);

        DirectionsMapRepository directionsMapRepository = new DirectionsMapRepository();
        PlacesMapRepository placesMapRepository = new PlacesMapRepository();
        mDirectionsMapPresenter = new DirectionsMapPresenter(this, directionsMapRepository);
        mPlacesMapPresenter = new PlacesMapPresenter(this, placesMapRepository);

        mPlaceAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragment_place_autocomplete);
        mPlaceAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d(TAG, "Place selected: " + place.getName());
                if (mMarker != null) mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 13));
            }

            @Override
            public void onError(Status status) {
                Log.e(TAG, "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        mButtonDirection.setOnClickListener(v -> onDirectionClick());
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_bank:
                            if (mMarker != null) {
                                mPlacesMapPresenter.downloadMapPlaces(mMarker.getPosition().latitude,
                                        mMarker.getPosition().longitude, getString(R.string.bank_nearby_place));
                                mMap.clear();
                            }
                            break;
                        case R.id.action_restaurant:
                            if (mMarker != null) {
                                mPlacesMapPresenter.downloadMapPlaces(mMarker.getPosition().latitude,
                                        mMarker.getPosition().longitude, getString(R.string.restaurant_nearby_place));
                                mMap.clear();
                            }
                            break;
                        case R.id.action_school:
                            if (mMarker != null) {
                                mPlacesMapPresenter.downloadMapPlaces(mMarker.getPosition().latitude,
                                        mMarker.getPosition().longitude, getString(R.string.school_nearby_place));
                                mMap.clear();
                            }
                            break;
                    }
                    return true;
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        enableMyLocationIfPermitted();
        mMap.setOnMapClickListener(newPosition -> {
            if (mMarker != null) mMarker.remove();
            mMarker = mMap.addMarker(new MarkerOptions().position(newPosition));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COURSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                break;
            }
        }
    }

    @Override
    public void drawingPolyline(PolylineOptions polylineOptions) {
        mPolylineFinal = mMap.addPolyline(polylineOptions);
    }

    @Override
    public void drawingPlaces(MarkerOptions markerOptions, LatLng latLng) {
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    private void onDirectionClick() {
        if (mPolylineFinal != null) {
            mPolylineFinal.remove();
        }
        if (mMarker != null) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            @SuppressLint("MissingPermission")
            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, true));

            LatLng origin = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng dest = mMarker.getPosition();
            mDirectionsMapPresenter.downloadMapDirection(origin, dest);
        }
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGTITUDE);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }
}