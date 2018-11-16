package com.rshtukaraxondevgroup.maptest.model.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsResult {
    @SerializedName("routes")
    @Expose
    private List<Route> routes;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }
}