package com.rshtukaraxondevgroup.maptest.model.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("legs")
    @Expose
    private List<Leg> legs;

    public List<Leg> getLegs() {
        return legs;
    }
}
