package com.demo_api.mybin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
        return new DetailHistoryAdapter.ViewHolder(binding, title);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.itemBinding.setItem(list.get(position));
        BinDetailHistory history = list.get(position);
        holder.bind(history, onItemClickListener);

//        holder.itemBinding.getRoot().setOnClickListener(v -> {
//            String date = holder.title.getText().toString();
//            Log.d("DEBUG1", date);
//            Intent intent = new Intent(context, EditBinDetailHistoryActivity.class);
//            intent.putExtra("HISTORY_NAME", history.getName());
//            intent.putExtra("HISTORY_TIME", "Thời điểm: " + date + history.getTime());
//            intent.putExtra("HISTORY_ACCURACY", "Độ chính xác: " + history.getAccuracy() + "%");
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DetailHistoryItemBinding itemBinding;
        private TextView title;

        public ViewHolder(DetailHistoryItemBinding itemBinding, TextView title) {
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
