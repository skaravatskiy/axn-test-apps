package com.rshtukaraxondevgroup.maptest.repository;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rshtukaraxondevgroup.maptest.utils.DirectionsJSONParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DirectionsMapRepository {
    private static final String TAG = DirectionsMapRepository.class.getCanonicalName();

    private DirectinsRepositoryListener mListener;
    private String data;

    public void download(LatLng origin, LatLng dest, DirectinsRepositoryListener listener) {
        mListener = listener;

        Observable.fromCallable(() -> {
            try {
                data = readUrl(getDirectionsUrl(origin, dest));
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            JSONObject jObject;
                            List<List<HashMap<String, String>>> routes = null;

                            try {
                                jObject = new JSONObject(result);
                                DirectionsJSONParser parser = new DirectionsJSONParser();

                                routes = parser.parse(jObject);
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }

                            ArrayList points;
                            PolylineOptions lineOptions = null;

                            for (int i = 0; i < routes.size(); i++) {
                                points = new ArrayList();
                                lineOptions = new PolylineOptions();

                                List<HashMap<String, String>> path = routes.get(i);

                                for (int j = 0; j < path.size(); j++) {
                                    HashMap<String, String> point = path.get(j);

                                    double lat = Double.parseDouble(point.get("lat"));
                                    double lng = Double.parseDouble(point.get("lng"));
                                    LatLng position = new LatLng(lat, lng);

                                    points.add(position);
                                }

                                lineOptions.addAll(points);
                                lineOptions.width(12);
                                lineOptions.color(Color.RED);
                                lineOptions.geodesic(true);
                            }
                            mListener.downloadSuccessful(lineOptions);
                        },
                        throwable -> mListener.downloadError(throwable));
    }

    private String readUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=driving";
        String key = "key=AIzaSyAofLR_mVLtPbw6eb6cJVqa9CTD3lXOT24";
        String parameters = str_origin + "&" + str_dest + "&" + mode + "&" + key;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }
}
