package com.rshtukaraxondevgroup.bluetoothtest.repository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rshtukaraxondevgroup.bluetoothtest.Constants.BASE_URL;

public class PhotosService implements IPhotosRepository{
    private static Retrofit mRetrofit = null;

    @Override
    public Retrofit getClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
