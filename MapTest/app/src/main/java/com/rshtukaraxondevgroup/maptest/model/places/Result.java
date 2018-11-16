package com.rshtukaraxondevgroup.maptest.model.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("reference")
    @Expose
    private String reference;


    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

}
