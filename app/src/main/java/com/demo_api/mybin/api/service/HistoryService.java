package com.demo_api.mybin.api.service;

import com.demo_api.mybin.api.BinApi;
import com.demo_api.mybin.api.HistoryApi;
import com.demo_api.mybin.model.Bin;
import com.demo_api.mybin.model.BinDetailHistory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class HistoryService {
    private static final String BASE_URL="https://longvnhue.pythonanywhere.com/";
    private HistoryApi api;

    public HistoryService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(HistoryApi.class);
    }

    public Single<List<BinDetailHistory>> getHistories(int day, int month, int year){
        return api.getDetailHistory(day, month, year);
    }


}
