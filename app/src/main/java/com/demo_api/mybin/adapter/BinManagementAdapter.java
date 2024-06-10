package com.demo_api.mybin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo_api.mybin.R;
import com.demo_api.mybin.databinding.BinManagementItemBinding;
import com.demo_api.mybin.databinding.DayHistoryItemBinding;
import com.demo_api.mybin.databinding.DetailHistoryItemBinding;
import com.demo_api.mybin.model.BinDetailHistory;
import com.demo_api.mybin.model.BinHistory;
import com.demo_api.mybin.view.history.EditBinDetailHistoryActivity;

import java.util.ArrayList;

public class BinManagementAdapter extends RecyclerView.Adapter<BinManagementAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BinDetailHistory> list;

    public BinManagementAdapter(ArrayList<BinDetailHistory> list) {
        this.list = list;
    }

    public BinManagementAdapter(Context context, ArrayList<BinDetailHistory> historyList) {
        this.context = context;
        this.list = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bin_management_item, parent, false);
        BinManagementItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bin_management_item, parent, false);

        return new BinManagementAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.itemBinding.setItem(list.get(position));
        BinDetailHistory history = list.get(position);
        holder.bind(history);
//        holder.itemBinding.getRoot().setOnClickListener(v -> {
//            Intent intent = new Intent(context, EditBinDetailHistoryActivity.class);
//            intent.putExtra("HISTORY_NAME", history.getName());
//            intent.putExtra("HISTORY_TIME", history.getTime());
//            intent.putExtra("HISTORY_ACCURACY", history.getAccuracy());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public BinManagementItemBinding itemBinding;
        private TextView title;

        public ViewHolder(BinManagementItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bind(BinDetailHistory history) {
            itemBinding.setItem(history);
            itemBinding.executePendingBindings();
        }
    }
}
