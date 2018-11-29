package com.rshtukaraxondevgroup.bluetoothtest.repository;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PhotosApi {
    @GET("photos")
    Single<List<PhotoModel>> getPhotos();
}
