package com.demo_api.mybin.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo_api.mybin.R;
import com.demo_api.mybin.api.service.BinApiService;
import com.demo_api.mybin.model.Bin;

import java.util.concurrent.TimeUnit;

import cjh.WaveProgressBarlibrary.WaveProgressBar;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static Bin static_bin;
    private WaveProgressBar metalWave;
    private WaveProgressBar plasticWave;
    private WaveProgressBar paperWave;
    private WaveProgressBar otherWave;

    private Disposable disposable;

    private BinApiService binApiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        metalWave = view.findViewById(R.id.metal_wave);
        plasticWave = view.findViewById(R.id.plastic_wave);
        paperWave = view.findViewById(R.id.paper_wave);
        otherWave = view.findViewById(R.id.other_wave);

        updateUI(static_bin);
        // Khởi tạo đối tượng BinApiService
        binApiService = new BinApiService();

        // Gọi API và cập nhật giao diện

        disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .flatMapSingle(tick -> binApiService.getBins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bin -> {
                            static_bin = bin;
                            // Xử lý khi nhận được dữ liệu từ API thành công
                            Log.d("SUCCESSS", "Metal: " + bin.getMetal());
                            Log.d("SUCCESSS", "Plastic: " + bin.getPlastic());
                            Log.d("SUCCESSS", "Paper: " + bin.getPaper());
                            Log.d("SUCCESSS", "Other: " + bin.getOther());
                            updateUI(static_bin); // Cập nhật giao diện
                        },
                        throwable -> {
                            // Xử lý khi gặp lỗi trong quá trình gọi API
                            Log.d("DEBUG", "FAIL" + throwable.getMessage());
                        }
                );
//        disposable = binApiService.getBinsPeriodically()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        bins -> {
//                            // Xử lý khi nhận được danh sách bins từ API
//                            updateUI(bins);
//                        },
//                        throwable -> {
//                            Log.d("ERROR", "error");
//                        }
//                );

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        metalWave.setProgress(62);
//                        plasticWave.setProgress(15);
//                        paperWave.setProgress(32);
//                        otherWave.setProgress(80);
//                    }
//                });
//            }
//        };

        //timer.schedule(timerTask, 0, 10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký khi Fragment bị hủy
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void updateUI(Bin bin) {
//         Cập nhật các ProgressBar dựa trên dữ liệu bins
        if (bin != null) {
            metalWave.setProgress(bin.getMetal());
            plasticWave.setProgress(bin.getPlastic());
            paperWave.setProgress(bin.getPaper());
            otherWave.setProgress(bin.getOther());

        }
    }
}