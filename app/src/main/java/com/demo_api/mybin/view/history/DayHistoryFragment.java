package com.demo_api.mybin.view.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo_api.mybin.R;
import com.demo_api.mybin.adapter.DayHistoryAdapter;
import com.demo_api.mybin.model.BinHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayHistoryFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayHistoryFragment extends Fragment {
    private ArrayList<BinHistory> dayHistoryList;
    private RecyclerView recyclerDay;
    private DayHistoryAdapter dayHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        dayHistoryList = new ArrayList<>();
        dayHistoryAdapter = new DayHistoryAdapter(dayHistoryList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerDay = view.findViewById(R.id.dayHistoryList);
        recyclerDay.setAdapter(dayHistoryAdapter);
        recyclerDay.setLayoutManager(new LinearLayoutManager(getContext()));

        createDayHistoryList();
        dayHistoryAdapter.notifyDataSetChanged();
    }

    private void createDayHistoryList() {
        dayHistoryList.add(new BinHistory(LocalDate.of(2024, 3, 21)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2024, 2, 12)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2024, 2, 5)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 12, 9)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 12, 12)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 12, 15)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 10, 7)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 9, 1)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 8, 5)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 8, 22)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 5, 29)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 2, 10)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2023, 1, 18)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2022, 11, 18)));
        dayHistoryList.add(new BinHistory(LocalDate.of(2022, 9, 21)));
    }
}