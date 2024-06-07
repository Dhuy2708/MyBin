package com.demo_api.mybin.view.home;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.Image;
import android.os.Build;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo_api.mybin.R;
import com.demo_api.mybin.api.service.BinApiService;
import com.demo_api.mybin.model.Bin;
import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.model.User;
import com.demo_api.mybin.view.MainActivity;
import com.demo_api.mybin.view.history.HistoryFragment;
import com.demo_api.mybin.view.user.LoginActivity;
import com.demo_api.mybin.view.user.RegistrationActivity;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cjh.WaveProgressBarlibrary.WaveProgressBar;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String CHANNEL_ID = "BinFullNotificationChannel";
    private static Bin static_bin;
    private WaveProgressBar metalWave;
    private WaveProgressBar plasticWave;
    private WaveProgressBar paperWave;
    private WaveProgressBar otherWave;
    private TextView numtime;
    private TextView nameHome;
    private CircleImageView imageView;
    private TextView promptLogin1;
    private Button btnLogin1;
    private LinearLayout mainPage;
    private RelativeLayout loginScreen;
    private ScrollView homePage;
    private ConstraintLayout trashHistory;
    private DatabaseHelper databaseHelper;
    private Disposable disposable;
    private BinApiService binApiService;
    private ExecutorService executorService;
    private static final String PREFS_NAME = "user_prefs";
    private static final String PREF_USER_ID = "user_id";

    private boolean isMetalNotified = false;
    private boolean isPlasticNotified = false;
    private boolean isPaperNotified = false;
    private boolean isOtherNotified = false;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        numtime = view.findViewById(R.id.numtime);
        nameHome = view.findViewById(R.id.name_home);
        promptLogin1 = view.findViewById(R.id.prompt_login1);
        btnLogin1 = view.findViewById(R.id.btn_login1);
        mainPage = view.findViewById(R.id.main_page);
        homePage = view.findViewById(R.id.scrollView2);
        imageView = view.findViewById(R.id.imageView);
        loginScreen = view.findViewById(R.id.login_screen);
        trashHistory = view.findViewById(R.id.trash_history);
        databaseHelper = new DatabaseHelper(getContext());
        if (isLoggedIn()) {
            loadUserProfile();
        } else {
            SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            showLoginPrompt();
        }
        btnLogin1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
//        trashHistory.setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.historyFragment);
//        });
        return view;

    }

    private void showLoginPrompt() {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
        homePage.setVisibility(View.GONE);
        loginScreen.setVisibility(View.VISIBLE);
        promptLogin1.setVisibility(View.VISIBLE);
        btnLogin1.setVisibility(View.VISIBLE);
    }

    private void loadUserProfile() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int userId = prefs.getInt(PREF_USER_ID, -1);
        Log.d("DEBUG1", String.valueOf(userId));
        if (userId != -1) {
            User user = databaseHelper.getUser(userId);
            if (user != null) {
                nameHome.setText(user.getName());
                imageView.setImageResource(R.drawable.default_avatar);
                promptLogin1.setVisibility(View.GONE);
                btnLogin1.setVisibility(View.GONE);
                loginScreen.setVisibility(View.GONE);
                homePage.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.contains(PREF_USER_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        metalWave = view.findViewById(R.id.metal_wave);
        plasticWave = view.findViewById(R.id.plastic_wave);
        paperWave = view.findViewById(R.id.paper_wave);
        otherWave = view.findViewById(R.id.other_wave);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int year = calendar.get(Calendar.YEAR);

        // Gọi API với ngày hiện tại
        updateUI_fl(static_bin);

        //Handle navigation khi ấn nút lịch sử rác
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false)
                .build();

        trashHistory.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.historyFragment, null, navOptions);
        });

        // Khởi tạo đối tượng BinApiService
        binApiService = new BinApiService();

        // Gọi API và cập nhật giao diện
        createNotificationChannel();


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
                            updateUI_fl(static_bin); // Cập nhật giao diện
                            getNumByDate(day, month, year);
                            checkBinsAndNotify(bin);
                        },
                        throwable -> {
                            // Xử lý khi gặp lỗi trong quá trình gọi API
                            Log.d("DEBUG", "FAIL" + throwable.getMessage());
                        }
                );

    }

    private void getNumByDate(int day, int month, int year) {
        binApiService.getNumByDate(day, month, year).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    Bin bin = response.body();

                    // Xử lý khi nhận được dữ liệu từ API thành công

                    Log.d("SUCCESSS", "NumTime: " + bin.getNumtime());

                    // Thực hiện cập nhật giao diện hoặc xử lý khác với dữ liệu từ API
                    updateUI_NumTime(bin.getNumtime());
                } else {
                    Log.d("DEBUG", "Numtime call not successful");
                }
            }

            @Override
            public void onFailure(Call<Bin> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.d("DEBUG", "FAIL" + t.getMessage());
            }
        });
    }

    private void checkBinsAndNotify(Bin bin) {
        if (bin.getMetal() >= 100 && !isMetalNotified) {
            sendNotification("Thùng kim loại đã đầy, hãy đi đổ");
            isMetalNotified = true;
        } else if (bin.getMetal() < 100) {
            isMetalNotified = false;
        }

        if (bin.getPlastic() >= 100 && !isPlasticNotified) {
            sendNotification("Thùng nhựa đã đầy, hãy đi đổ");
            isPlasticNotified = true;
        } else if (bin.getPlastic() < 100) {
            isPlasticNotified = false;
        }

        if (bin.getPaper() >= 100 && !isPaperNotified) {
            sendNotification("Thùng giấy đã đầy, hãy đi đổ");
            isPaperNotified = true;
        } else if (bin.getPaper() < 100) {
            isPaperNotified = false;
        }

        if (bin.getOther() >= 100 && !isOtherNotified) {
            sendNotification("Thùng các loại rác khác đã đầy, hãy đi đổ");
            isOtherNotified = true;
        } else if (bin.getOther() < 100) {
            isOtherNotified = false;
        }
    }

    private void sendNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // Thay R.drawable.ic_notification bằng tài nguyên icon của bạn
                .setContentTitle("Bin Full Alert")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Bin Full Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký khi Fragment bị hủy
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    private void updateUI_fl(Bin bin) {
//         Cập nhật các ProgressBar dựa trên dữ liệu bins
        if (bin != null) {


            metalWave.setProgress(bin.getMetal());
            plasticWave.setProgress(bin.getPlastic());
            paperWave.setProgress(bin.getPaper());
            otherWave.setProgress(bin.getOther());

        }
    }

    private void updateUI_NumTime(int num){
        numtime.setText("Hôm nay bạn đã đổ rác " + num + " lần");
    }
}