package com.rshtukaraxondevgroup.maptest.model.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResults {
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
