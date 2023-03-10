package com.donghh.quanlynhahangdouong.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.quanlynhahangdouong.MainActivity;
import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ActivityLoginBinding;
import com.donghh.quanlynhahangdouong.ui.Common;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    String employee = "nhanvien";
    String manager = "quanly";
    String password = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            binding.tvLogin.setOnClickListener(v -> {
                if (validateLogin()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    Common.putBoolean(LoginActivity.this, Common.IS_LOGIN, true);

                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            });

            binding.tipUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilUserName.setError(null);

                }
            });

            binding.tipUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilUserName.setError(null);

                }
            });

            binding.tipPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilPassword.setError(null);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean validateLogin() {
        //user name
        if (binding.tipUserName.getText().toString().isEmpty()) {
            binding.tilUserName.setError(getString(R.string.not_user_name));
        } else {
            binding.tilUserName.setError(null);
        }

        //password
        if (binding.tipPassword.getText().toString().isEmpty()) {
            binding.tilPassword.setError(getString(R.string.not_password));
        } else {
            binding.tilPassword.setError(null);
        }
        //
        if ((binding.tipUserName.getText().toString().trim().equals(employee)
                || binding.tipUserName.getText().toString().trim().equals(manager)) && binding.tipPassword.getText().toString().trim().equals(password)) {

            if (binding.tipUserName.getText().toString().trim().equals(manager)){
                Common.putBoolean(LoginActivity.this, Common.IS_MANAGER, true);
            }else {
                Common.putBoolean(LoginActivity.this, Common.IS_MANAGER, false);

            }
            binding.tilUserName.setError(null);
            binding.tilPassword.setError(null);
            return true;
        }
        return false;
    }


}
