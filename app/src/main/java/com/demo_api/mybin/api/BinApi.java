package com.demo_api.mybin.api;

import com.demo_api.mybin.model.Bin;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BinApi {
    @GET("getALL_FR")
    Single<Bin> getBin();

    @GET("getNum_byDate")
    Call<Bin> getNumByDate(
            @Query("day") int day,
            @Query("month") int month,
            @Query("year") int year
    );

}
