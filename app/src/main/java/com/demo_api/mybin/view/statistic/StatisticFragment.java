package com.demo_api.mybin.view.statistic;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo_api.mybin.R;
import com.demo_api.mybin.api.service.BinApiService;
import com.demo_api.mybin.model.Bin;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment } factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {

    private BinApiService binApiService;
    private BarChart barChart;
    private LineChart lineChart;
    private PieChart pieChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        // Initialize charts
        barChart = view.findViewById(R.id.barChart);
        lineChart = view.findViewById(R.id.lineChart);
        pieChart = view.findViewById(R.id.pieChart);

        setupBarChart();
        setupLineChart();
        setupPieChart();

        return view;
    }

    private void setupBarChart() {
        // Tạo dữ liệu mẫu cho BarChart
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5)); // Thứ Hai
        entries.add(new BarEntry(1, 8)); // Thứ Ba
        entries.add(new BarEntry(2, 6)); // Thứ Tư
        entries.add(new BarEntry(3, 7)); // Thứ Năm
        entries.add(new BarEntry(4, 10)); // Thứ Sáu
        entries.add(new BarEntry(5, 4)); // Thứ Bảy
        entries.add(new BarEntry(6, 2)); // Chủ Nhật

        BarDataSet dataSet = new BarDataSet(entries, "Số lượng đổ rác");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Tùy chỉnh biểu đồ
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"}));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Cập nhật biểu đồ
        barChart.invalidate();
    }

    private void setupLineChart() {
        // Dữ liệu mẫu cho LineChart
        ArrayList<Entry> metalEntries = new ArrayList<>();
        metalEntries.add(new Entry(0, 2));
        metalEntries.add(new Entry(1, 3));
        metalEntries.add(new Entry(2, 0));
        metalEntries.add(new Entry(3, 5));
        metalEntries.add(new Entry(4, 6));
        metalEntries.add(new Entry(5, 3));
        metalEntries.add(new Entry(6, 4));

        LineDataSet metalDataSet = new LineDataSet(metalEntries, "Metal");
        metalDataSet.setColor(Color.RED);

        ArrayList<Entry> plasticEntries = new ArrayList<>();
        plasticEntries.add(new Entry(0, 5));
        plasticEntries.add(new Entry(1, 6));
        plasticEntries.add(new Entry(2, 3));
        plasticEntries.add(new Entry(3, 7));
        plasticEntries.add(new Entry(4, 8));
        plasticEntries.add(new Entry(5, 5));
        plasticEntries.add(new Entry(6, 6));

        LineDataSet plasticDataSet = new LineDataSet(plasticEntries, "Plastic");
        plasticDataSet.setColor(Color.BLUE);

        ArrayList<Entry> paperEntries = new ArrayList<>();
        paperEntries.add(new Entry(0, 4));
        paperEntries.add(new Entry(1, 5));
        paperEntries.add(new Entry(2, 1));
        paperEntries.add(new Entry(3, 4));
        paperEntries.add(new Entry(4, 5));
        paperEntries.add(new Entry(5, 4));
        paperEntries.add(new Entry(6, 3));

        LineDataSet paperDataSet = new LineDataSet(paperEntries, "Paper");
        paperDataSet.setColor(Color.GREEN);

        ArrayList<Entry> otherEntries = new ArrayList<>();
        otherEntries.add(new Entry(0, 3));
        otherEntries.add(new Entry(1, 4));
        otherEntries.add(new Entry(2, 2));
        otherEntries.add(new Entry(3, 3));
        otherEntries.add(new Entry(4, 4));
        otherEntries.add(new Entry(5, 3));
        otherEntries.add(new Entry(6, 2));

        LineDataSet otherDataSet = new LineDataSet(otherEntries, "Other");
        otherDataSet.setColor(Color.MAGENTA);

        LineData lineData = new LineData(metalDataSet, plasticDataSet, paperDataSet, otherDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"}));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        lineChart.invalidate();
    }

    private void setupPieChart() {
        // Dữ liệu mẫu cho PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30, "Metal"));
        entries.add(new PieEntry(25, "Plastic"));
        entries.add(new PieEntry(20, "Paper"));
        entries.add(new PieEntry(25, "Other"));

        PieDataSet dataSet = new PieDataSet(entries, "Loại vật liệu");
        dataSet.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA});
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }



}