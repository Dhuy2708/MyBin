package com.demo_api.mybin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.demo_api.mybin.view.history.DayHistoryFragment;
import com.demo_api.mybin.view.history.MonthHistoryFragment;
import com.demo_api.mybin.view.history.YearHistoryFragment;

public class HistoryPagerAdapter extends FragmentStateAdapter {
    public HistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HistoryPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DayHistoryFragment();
            case 1:
                return new MonthHistoryFragment();
            case 2:
                return new YearHistoryFragment();
            default:
                return new DayHistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
