package com.rshtukaraxondevgroup.maptest.repository;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rshtukaraxondevgroup.maptest.utils.PlacesJSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.rshtukaraxondevgroup.maptest.Constants.PROXIMITY_RADIUS;

public class PlacesMapRepository {
    private static final String TAG = PlacesMapRepository.class.getCanonicalName();

    private PlacesRepositoryListener mListener;
    private String googlePlacesData;

    public void download(double latitude, double longitude, String nearbyPlace, PlacesRepositoryListener listener) {
        mListener = listener;

        Observable.fromCallable(() -> {
            try {
                googlePlacesData = readUrl(getPlacesUrl(latitude, longitude, nearbyPlace));
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return googlePlacesData;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            List<HashMap<String, String>> nearbyPlaceList;
                            PlacesJSONParser parser = new PlacesJSONParser();
                            nearbyPlaceList = parser.parse(result);
                            Log.d(TAG, "called parse method");

                            for (int i = 0; i < nearbyPlaceList.size(); i++) {
                                MarkerOptions markerOptions = new MarkerOptions();
                                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

                                String placeName = googlePlace.get("place_name");
                                String vicinity = googlePlace.get("vicinity");
                                double lat = Double.parseDouble(googlePlace.get("lat"));
                                double lng = Double.parseDouble(googlePlace.get("lng"));

                                LatLng latLng = new LatLng(lat, lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName + " : " + vicinity);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                                mListener.downloadSuccessful(markerOptions, latLng);
                            }
                        },
                        throwable -> mListener.downloadError(throwable));
    }

    private String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        Log.d(TAG, "Returning data= " + data);

        return data;
    }

    private String getPlacesUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=AIzaSyAofLR_mVLtPbw6eb6cJVqa9CTD3lXOT24");
        return googlePlaceUrl.toString();
    }
}
