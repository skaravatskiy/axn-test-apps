package com.rshtukaraxondevgroup.maptest.model.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;

    public Polyline getPolyline() {
        return polyline;
    }
}
