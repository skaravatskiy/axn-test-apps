package com.rshtukaraxondevgroup.maptest.model.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Polyline {
    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }
}
