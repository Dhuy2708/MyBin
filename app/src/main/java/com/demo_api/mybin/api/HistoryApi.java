package com.demo_api.mybin.api;

import com.demo_api.mybin.model.Bin;
import com.demo_api.mybin.model.BinDetailHistory;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryApi {
    @GET("getHistory_byDate")
    Single<List<BinDetailHistory>> getDetailHistory(
            @Query("day") int day,
            @Query("month") int month,
            @Query("year") int year
    );




}
