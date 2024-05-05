package com.demo_api.mybin.api;

import com.demo_api.mybin.model.BinDetailHistory;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface HistoryApi {
    @GET("getHistory_byDate?day=24&month=4&year=2024")
    Single<List<BinDetailHistory>> getDetailHistory();
}
