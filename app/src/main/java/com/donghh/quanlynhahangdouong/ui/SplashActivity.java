package com.donghh.quanlynhahangdouong.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.quanlynhahangdouong.MainActivity;
import com.donghh.quanlynhahangdouong.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 2000);
            if (Common.getBoolean(SplashActivity.this, Common.IS_LOGIN)) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
