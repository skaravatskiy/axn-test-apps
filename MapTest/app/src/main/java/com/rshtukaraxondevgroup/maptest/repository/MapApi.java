package com.rshtukaraxondevgroup.maptest.repository;

import com.rshtukaraxondevgroup.maptest.model.directions.DirectionsResult;
import com.rshtukaraxondevgroup.maptest.model.places.PlacesResults;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApi {
    @GET("/maps/api/directions/json")
    Single<DirectionsResult> getDirectionsResults(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("mode") String mode,
            @Query("key") String key);

    @GET("/maps/api/place/nearbysearch/json")
    Single<PlacesResults> getPlacesResults(
            @Query("location") String location,
            @Query("radius") Integer radius,
            @Query("type") String types,
            @Query("key") String key);
}