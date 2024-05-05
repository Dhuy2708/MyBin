
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
import com.demo_api.mybin.adapter.MonthHistoryAdapter;
import com.demo_api.mybin.databinding.YearHistoryItemBinding;
import com.demo_api.mybin.model.BinHistory;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthHistoryFragment extends Fragment {
    private ArrayList<BinHistory> monthHistoryList;
    private RecyclerView recyclerMonth;
    private MonthHistoryAdapter monthHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        monthHistoryList = new ArrayList<>();
        monthHistoryAdapter = new MonthHistoryAdapter(monthHistoryList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerMonth = view.findViewById(R.id.monthHistoryList);
        recyclerMonth.setAdapter(monthHistoryAdapter);
        recyclerMonth.setLayoutManager(new LinearLayoutManager(getContext()));

        monthHistoryAdapter.notifyDataSetChanged();
    }

    private void createDayHistoryList() {
        monthHistoryList.add(new BinHistory(LocalDate.of(2024, 3, 21)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2024, 2, 12)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2024, 2, 5)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 12, 9)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 10, 7)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 9, 1)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 8, 5)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 5, 29)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 2, 10)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2023, 1, 18)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2022, 11, 18)));
        monthHistoryList.add(new BinHistory(LocalDate.of(2022, 9, 21)));
    }
}