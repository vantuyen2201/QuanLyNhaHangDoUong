package com.donghh.quanlynhahangdouong.ui.selectdrink;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donghh.quanlynhahangdouong.databinding.ActivitySelectDrinkBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.detailbook.DrinkBookAdapter;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;
import com.donghh.quanlynhahangdouong.ui.event.ListDrinkSelectEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SelectDrinkActivity extends AppCompatActivity {

    private ActivitySelectDrinkBinding binding;
    private DatabaseHandler databaseHandler;

    private DrinkBookAdapter drinkBookAdapter;

    private  ArrayList<Drink> listDrink = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDrinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHandler = new DatabaseHandler(SelectDrinkActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        initRecyclerListDrink();

        initListener();
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Drink> listDrinkSelect= new ArrayList<>();
                for (Drink drink : listDrink) {
                    if (drink.getAmount()>0){
                        listDrinkSelect.add(drink);
                    }
                }

                EventBus.getDefault().post(new ListDrinkSelectEvent(listDrinkSelect));
                finish();
                
            }
        });
    }

    private void initRecyclerListDrink() {
        try {
            listDrink = databaseHandler.getAllDrink();
            binding.rvDrink.setLayoutManager(new LinearLayoutManager(SelectDrinkActivity.this, LinearLayoutManager.VERTICAL, false));
            drinkBookAdapter = new DrinkBookAdapter(listDrink);
            binding.rvDrink.setAdapter(drinkBookAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}