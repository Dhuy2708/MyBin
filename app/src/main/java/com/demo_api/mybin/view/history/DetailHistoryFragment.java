package com.demo_api.mybin.view.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo_api.mybin.R;
import com.demo_api.mybin.adapter.DetailHistoryAdapter;
import com.demo_api.mybin.api.service.HistoryService;
import com.demo_api.mybin.model.BinDetailHistory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailHistoryFragment extends Fragment {
    private HistoryService apiService;
    private RecyclerView recyclerView;
    private ArrayList<BinDetailHistory> history_list;
    private DetailHistoryAdapter historyAdapter;

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
        return inflater.inflate(R.layout.fragment_detail_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        history_list = new ArrayList<BinDetailHistory>();
        historyAdapter = new DetailHistoryAdapter(history_list);

        recyclerView = view.findViewById(R.id.detailHistoryList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        apiService = new HistoryService();
        apiService.getHistories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<BinDetailHistory>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<BinDetailHistory> histories) {

                        for(BinDetailHistory history : histories){
                            Log.d("SUCCESSS", history.getName());
                            Log.d("SUCCESSS", history.getTime());
                            Log.d("SUCCESSS", history.getAccuracy());

                            history_list.add(history);
                            historyAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "FAIL" + e.getMessage());
                    }
                });
    }
}