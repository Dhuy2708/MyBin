package com.demo_api.mybin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailHistoryAdapter extends RecyclerView.Adapter<DetailHistoryAdapter.ViewHolder>{
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<BinDetailHistory> list;
    public DetailHistoryAdapter(ArrayList<BinDetailHistory> list){
        this.list = list;
    }
    public DetailHistoryAdapter(Context context, ArrayList<BinDetailHistory> historyList) {
        this.context = context;
        this.list = historyList;
    }
    public interface OnItemClickListener {
        void onItemClick(BinDetailHistory binDetailHistory);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_history_item, parent, false );
        View view1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_history, parent, false );
        TextView title = (TextView) view1.findViewById(R.id.dateTextView);

        DetailHistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.detail_history_item, parent, false);
        return new DetailHistoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.setItem(list.get(position));
        Picasso.get().load(list.get(position).getImg()).into(holder.itemBinding.binImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DetailHistoryItemBinding itemBinding;
        private TextView title;

        public ViewHolder(DetailHistoryItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
            this.title = title;
        }

        void bind(BinDetailHistory history, final OnItemClickListener listener) {
            itemBinding.setItem(history);
            itemBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(history);
                }
            });
        }
    }
}
