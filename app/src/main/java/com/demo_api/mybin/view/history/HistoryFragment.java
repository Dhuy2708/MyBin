package com.demo_api.mybin.view.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.R;
import com.demo_api.mybin.adapter.HistoryPagerAdapter;
import com.demo_api.mybin.model.User;
import com.demo_api.mybin.view.user.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private HistoryPagerAdapter historyPageAdapter;
    private TextView promptLogin2;
    private Button btnLogin2;
    private RelativeLayout loginScreen;
    private DatabaseHelper databaseHelper;
    private static final String PREFS_NAME = "user_prefs";
    private static final String PREF_USER_ID = "user_id";

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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        tabLayout = view.findViewById(R.id.historyTabLayout);
        viewPager2 = view.findViewById(R.id.historyViewPager);
        promptLogin2 = view.findViewById(R.id.prompt_login2);
        btnLogin2 = view.findViewById(R.id.btn_login2);
        loginScreen = view.findViewById(R.id.login_screen2);
        databaseHelper = new DatabaseHelper(getContext());
        if (isLoggedIn()) {
            loadHistory();
        } else {
            SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            showLoginPrompt();
        }
        btnLogin2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void showLoginPrompt() {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
        tabLayout.setVisibility(View.GONE);
        viewPager2.setVisibility(View.GONE);
        loginScreen.setVisibility(View.VISIBLE);
        promptLogin2.setVisibility(View.VISIBLE);
        btnLogin2.setVisibility(View.VISIBLE);
    }

    private void loadHistory() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int userId = prefs.getInt(PREF_USER_ID, -1);
        Log.d("DEBUG1", String.valueOf(userId));
        if (userId != -1) {
            User user = databaseHelper.getUser(userId);
            if (user != null) {
                promptLogin2.setVisibility(View.GONE);
                btnLogin2.setVisibility(View.GONE);
                loginScreen.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
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

        tabLayout = view.findViewById(R.id.historyTabLayout);
        viewPager2 = view.findViewById(R.id.historyViewPager);
        historyPageAdapter = new HistoryPagerAdapter(this);
        viewPager2.setAdapter(historyPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });
    }
}