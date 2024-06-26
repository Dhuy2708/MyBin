package com.demo_api.mybin.view.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.demo_api.mybin.R;
import com.demo_api.mybin.adapter.DetailHistoryAdapter;
import com.demo_api.mybin.api.service.HistoryService;
import com.demo_api.mybin.model.BinDetailHistory;
import com.demo_api.mybin.model.BinHistory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailHistoryFragment } factory method to
 * create an instance of this fragment.
 */
public class DetailHistoryFragment extends Fragment {
    private HistoryService apiService;
    private RecyclerView recyclerView;
    private ArrayList<BinDetailHistory> history_list;
    private BinHistory binReceived;
    private DetailHistoryAdapter historyAdapter;
    private ImageButton backToHistoryBtn;
    private TextView dateTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            binReceived = (BinHistory)getArguments().getSerializable("binHistory");
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

        dateTV = view.findViewById(R.id.dateTextView);
        dateTV.setText(binReceived.getDayMonthYear());

        view.findViewById(R.id.backToHistoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang danh sách (list) khi nút back được nhấn
                NavHostFragment.findNavController(DetailHistoryFragment.this).navigateUp();
            }
        });

        history_list = new ArrayList<BinDetailHistory>();
        historyAdapter = new DetailHistoryAdapter(getContext(), history_list);

        recyclerView = view.findViewById(R.id.detailHistoryList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        apiService = new HistoryService();
        apiService.getHistories(binReceived.getDate().getDayOfMonth(), binReceived.getDate().getMonthValue(), binReceived.getDate().getYear())
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

        historyAdapter.setOnItemClickListener(binDetailHistory -> {
            String dateText = dateTV.getText().toString();

            // Create an Intent to open the edit activity
            Intent intent = new Intent(getContext(), EditBinDetailHistoryActivity.class);

            // Pass the selected item's data to the new activity
            intent.putExtra("HISTORY_NAME", binDetailHistory.getName());
            intent.putExtra("HISTORY_TIME", "Thời điểm: " + dateText + " " + binDetailHistory.getTime());
            intent.putExtra("HISTORY_ACCURACY", "Độ chính xác: " + binDetailHistory.getAccuracy() + "%");
//            intent.putExtra("DATE_TEXT", dateText);

            // Start the new activity
            startActivity(intent);
        });
    }
}