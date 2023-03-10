package com.donghh.quanlynhahangdouong.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.FragmentSettingBinding;
import com.donghh.quanlynhahangdouong.ui.Common;
import com.donghh.quanlynhahangdouong.ui.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        binding = FragmentSettingBinding.bind(view);

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.putBoolean(getContext(), Common.IS_LOGIN, false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}