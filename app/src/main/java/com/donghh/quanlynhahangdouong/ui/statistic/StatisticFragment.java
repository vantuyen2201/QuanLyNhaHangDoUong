package com.donghh.quanlynhahangdouong.ui.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.FragmentNotificationsBinding;
import com.donghh.quanlynhahangdouong.ui.Common;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Bill;
import com.donghh.quanlynhahangdouong.ui.event.UpdateBookEvent;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private DatabaseHandler databaseHandler;
    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> barTitleArrayList;


    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    List<Integer> listYear = new ArrayList<>();

    int yearSelect;
    //danh sách hóa đơn
    private ArrayList<Bill> listBill = new ArrayList<>();

    public static StatisticFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StatisticFragment fragment = new StatisticFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        binding = FragmentNotificationsBinding.bind(view);


        initData();

        return view;
    }

    private void initSpinnerYear() {
        try {
            //lấy 10 năm trước
            Calendar calendar = Calendar.getInstance();
            int yearCurrent = calendar.get(Calendar.YEAR);
            for (int i = yearCurrent; i > (yearCurrent - 10); i--) {
                listYear.add(i);
            }
            SpinnerYearAdapter spinnerAdapter = new SpinnerYearAdapter(getContext(), listYear);

            binding.spYear.setAdapter(spinnerAdapter);

            //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
            binding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //đối số postion là vị trí phần tử trong list Data
//                    String msg = "position :" + position + " value :" + list.get(position);
//                    Toast.makeText(SpinnerActivity.this, msg, Toast.LENGTH_SHORT).show();
                    yearSelect = listYear.get(position);
                    initChart();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
//                    Toast.makeText(SpinnerActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void OnEvent(UpdateBookEvent event) {
        initData();
    }

    private void initData() {
        try {
            databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

            Calendar calendar = Calendar.getInstance();
            yearSelect= calendar.get(Calendar.YEAR);

            listBill = databaseHandler.getAllBill();
            initMonth();
            initChart();
            initSpinnerYear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMonth() {
        barTitleArrayList = new ArrayList<>();
        barTitleArrayList.add("T1");
        barTitleArrayList.add("T2");
        barTitleArrayList.add("T3");
        barTitleArrayList.add("T4");
        barTitleArrayList.add("T5");
        barTitleArrayList.add("T6");
        barTitleArrayList.add("T7");
        barTitleArrayList.add("T8");
        barTitleArrayList.add("T9");
        barTitleArrayList.add("T10");
        barTitleArrayList.add("T11");
        barTitleArrayList.add("T12");
    }

    private void initChart() {
        try {
            // calling method to get bar entries.
            getBarEntries();


            // creating a new bar data set.
            barDataSet = new BarDataSet(barEntriesArrayList, "");

            // creating a new bar data and
            // passing our bar data set.
            barData = new BarData(barDataSet);
//            barData.setBarWidth(barWidth);

            // below line is to set data
            // to our bar chart.
            binding.barChart.setData(barData);

            // adding color to our bar data set.
//            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            // setting text color.
            barDataSet.setValueTextColor(Color.BLACK);

            // setting text size
            barDataSet.setValueTextSize(13f);
            binding.barChart.getDescription().setEnabled(false);
            binding.barChart.getLegend().setEnabled(false);
            binding.barChart.getAxisRight().setDrawLabels(false);
            binding.barChart.setPinchZoom(false);
            binding.barChart.getAxisLeft().setStartAtZero(true);

            XAxis xAxis = binding.barChart.getXAxis();
            xAxis.setEnabled(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(barTitleArrayList));
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelCount(barTitleArrayList.size());
            xAxis.setTextSize(11);
            xAxis.setCenterAxisLabels(true);

            binding.barChart.notifyDataSetChanged();
            binding.barChart.invalidate();

//            Legend l = binding.barChart.getLegend();
//            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//            l.setDrawInside(false);
//            l.setForm(Legend.LegendForm.SQUARE);
//            l.setFormSize(9f);
//            l.setTextSize(11f);
//            l.setXEntrySpace(4f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        //get ngày bắt đầu và ngày kết thúc từng tháng trong năm đnag chọn

        //Lấy danh hóa đơn 12 tháng của năm đang chọn
        for (int i = 1; i <= 12; i++) {
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.set(Calendar.YEAR, yearSelect);
            calendarStart.set(Calendar.MONTH, i-1);
            calendarStart.set(Calendar.DATE, 1);
            String startDate = Common.formatDateToString(calendarStart.getTime());

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.set(Calendar.YEAR, yearSelect);
            calendarEnd.set(Calendar.MONTH, i-1);
            calendarEnd.set(Calendar.DATE, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
            String startEnd = Common.formatDateToString(calendarEnd.getTime());


            ArrayList<Bill> billsMonth = databaseHandler.getAllBillByForDate(startDate, startEnd);

            double totalMoney = 0;
            if (billsMonth.size() > 0) {
                for (Bill bill : billsMonth) {
                    totalMoney += bill.getTotalMoney();
                }
            }
            barEntriesArrayList.add(new BarEntry((float) ((float) i - 0.5), (float) totalMoney / 1000));


        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}