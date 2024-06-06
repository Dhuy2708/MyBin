package com.demo_api.mybin.api.service;

import com.demo_api.mybin.api.BinApi;
import com.demo_api.mybin.model.Bin;

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
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BinApiService {
    private static final String BASE_URL="https://longvnhue.pythonanywhere.com/";
    private BinApi api;

    public BinApiService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(BinApi.class);
    }

    public Call<Bin> getNumByDate(int day, int month, int year){
        return api.getNumByDate(day, month, year);
    }

    public Observable<Bin> getBinsPeriodically() {
        // Sử dụng Observable.interval để tạo sự kiện định kỳ mỗi 2 giây
        return Observable.interval(0, 2, TimeUnit.SECONDS) // Khởi động ngay và gửi sự kiện mỗi 2 giây
                .flatMap(__ -> api.getBin().toObservable()) // Gửi yêu cầu lấy danh sách bins
                .subscribeOn(Schedulers.io()) // Thực hiện trên luồng I/O
                .observeOn(AndroidSchedulers.mainThread()); // Nhận kết quả và cập nhật trên luồng UI
    }

    public Single<Bin> getBins(){
        return api.getBin();
    }
}
