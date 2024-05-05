package com.demo_api.mybin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.demo_api.mybin.R;
import com.demo_api.mybin.databinding.DayHistoryItemBinding;
import com.demo_api.mybin.model.BinHistory;

import java.util.ArrayList;

public class DayHistoryAdapter extends RecyclerView.Adapter<DayHistoryAdapter.ViewHolder> {
    private ArrayList<BinHistory> list;

    public DayHistoryAdapter(ArrayList<BinHistory> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_history_item, parent, false );
        DayHistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.day_history_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DayHistoryItemBinding binding;

        public ViewHolder(DayHistoryItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.binding = itemBinding;

            binding.historyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.detailHistoryList);
                }
            });

        }
    }
}
