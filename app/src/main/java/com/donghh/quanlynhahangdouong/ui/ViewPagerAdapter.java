package com.donghh.quanlynhahangdouong.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    private Context mContext;
    private ArrayList<Fragment> list;

    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}
