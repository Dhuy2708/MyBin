package com.demo_api.mybin.api;

import com.demo_api.mybin.model.Bin;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface BinApi {
    @GET("test")
    Single<Bin> getBin();


}
