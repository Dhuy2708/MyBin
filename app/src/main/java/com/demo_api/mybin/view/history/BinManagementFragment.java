package com.demo_api.mybin.view.history;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.R;
import com.demo_api.mybin.adapter.BinManagementAdapter;
import com.demo_api.mybin.adapter.DetailHistoryAdapter;
import com.demo_api.mybin.model.BinDetailHistory;
import com.demo_api.mybin.model.BinHistory;
import com.demo_api.mybin.view.MainActivity;
import com.demo_api.mybin.view.home.HomeFragment;

import java.util.ArrayList;

public class BinManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<BinDetailHistory> binList;
    private BinHistory binReceived;
    private BinManagementAdapter historyAdapter;
    DatabaseHelper databaseHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            binReceived = (BinHistory)getArguments().getSerializable("binHistory");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_bin, container, false);
        binList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.detailHistoryList1);
        historyAdapter = new BinManagementAdapter(getContext(), binList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper = new DatabaseHelper(getContext());
        binList.clear();
        binList.addAll(databaseHelper.getAllBin());
        Log.d("DEBUG1", binList.get(0).getName());
        historyAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false)
                .build();
        view.findViewById(R.id.backToHistoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang danh sách (list) khi nút back được nhấn
                Navigation.findNavController(v).navigate(R.id.homeFragment, null, navOptions);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback
        );
    }
}
