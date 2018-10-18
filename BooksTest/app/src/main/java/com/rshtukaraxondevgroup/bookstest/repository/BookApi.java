package com.rshtukaraxondevgroup.bookstest.repository;

import com.rshtukaraxondevgroup.bookstest.model.BookModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApi {

    @GET("books")
    Single<List<BookModel>> getBooks(@Query("page") int page);
}
