package com.donghh.quanlynhahangdouong.ui.statistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.donghh.quanlynhahangdouong.R;

import java.util.List;

class SpinnerYearAdapter extends BaseAdapter {

    private List<Integer> list;

    private Context context;

    public SpinnerYearAdapter(Context context,
                              List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        Integer language = (Integer) this.getItem(position);
        return language;
        // return position; (Return position if you need).
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_year, parent, false);
        AppCompatTextView label= view.findViewById(R.id.tvYear);
        label.setText(String.valueOf(list.get(position)));
        return view;
    }
}