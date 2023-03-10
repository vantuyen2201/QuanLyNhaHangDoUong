package com.donghh.quanlynhahangdouong.ui.detailbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donghh.quanlynhahangdouong.databinding.FragmentDetailBookBinding;
import com.donghh.quanlynhahangdouong.ui.Common;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Bill;
import com.donghh.quanlynhahangdouong.ui.entity.Book;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;
import com.donghh.quanlynhahangdouong.ui.event.ListDrinkSelectEvent;
import com.donghh.quanlynhahangdouong.ui.event.UpdateBookEvent;
import com.donghh.quanlynhahangdouong.ui.order.OrderActivity;
import com.donghh.quanlynhahangdouong.ui.selectdrink.SelectDrinkActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailBookActivity extends AppCompatActivity {

    public static String DetailBook = "DetailBook";
    private FragmentDetailBookBinding binding;
    private DatabaseHandler databaseHandler;

    private DrinkBookAdapter drinkBookAdapter;

    private Book book;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = FragmentDetailBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHandler = new DatabaseHandler(DetailBookActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        book = getIntent().getParcelableExtra(DetailBook);
        binding.tvBook.setText("Bàn " + book.getId());
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

        binding.flbAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailBookActivity.this, SelectDrinkActivity.class);
                startActivity(intent);
            }
        });

        binding.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Drink> listDrink = new ArrayList<>();
                    for (Drink drink : drinkBookAdapter.getDrinkList()) {
                        if (drink.getAmount() > 0) {
                            listDrink.add(drink);
                        }
                    }
                    if (listDrink.size() > 0) {
                        databaseHandler.updateBookById(book.getId(), new Gson().toJson(listDrink));
                    } else {
                        databaseHandler.updateBookById(book.getId(), "");

                    }

                    EventBus.getDefault().post(new UpdateBookEvent());
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        binding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double totalMoney = 0;
                    ArrayList<Drink> listDrink = new ArrayList<>();
                    for (Drink drink : drinkBookAdapter.getDrinkList()) {
                        if (drink.getAmount() > 0) {
                            listDrink.add(drink);
                            totalMoney += drink.getAmount() * drink.getPrice();
                        }
                    }

                    Bill bill = new Bill();
                    bill.setListDrink(new Gson().toJson(listDrink));
                    bill.setTotalMoney(totalMoney);
                    Calendar calendar = Calendar.getInstance();
                    bill.setDateBill(Common.formatDateToString(calendar.getTime()));
                    //lưu hóa đơn
                    databaseHandler.addBill(bill);

                    //xóa đồ uống của bàn đó
                    databaseHandler.updateBookById(book.getId(), "");
                    EventBus.getDefault().post(new UpdateBookEvent());
                    finish();

                    Log.d("size", "" + binding.rlData.getWidth() + " " + binding.rlData.getWidth());

                    Intent intent = new Intent(DetailBookActivity.this, OrderActivity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Subscribe
    public void OnEvent(ListDrinkSelectEvent event) {
        ArrayList<Drink> listDrink = new ArrayList<>();
        if (book.getListDrink() != null) {
            Type listType = new TypeToken<ArrayList<Drink>>() {
            }.getType();
            listDrink = new Gson().fromJson(book.getListDrink(), listType);
            if (listDrink == null) {
                listDrink = new ArrayList<>();
            }

        }
        listDrink.addAll(event.getList());
        checkListDrink(listDrink);
        book.setListDrink(new Gson().toJson(listDrink));
        drinkBookAdapter.updateDrinkList(listDrink);

    }

    private void initRecyclerListDrink() {
        try {
            binding.rvDrink.setLayoutManager(new LinearLayoutManager(DetailBookActivity.this, LinearLayoutManager.VERTICAL, false));

            ArrayList<Drink> listDrink = new ArrayList<>();
            if (book.getListDrink() != null) {
                Type listType = new TypeToken<ArrayList<Drink>>() {
                }.getType();
                listDrink = new Gson().fromJson(book.getListDrink(), listType);
                if (listDrink == null) {
                    listDrink = new ArrayList<>();
                }

            }
            drinkBookAdapter = new DrinkBookAdapter(listDrink);
            binding.rvDrink.setAdapter(drinkBookAdapter);

            checkListDrink(listDrink);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkListDrink(ArrayList<Drink> listDrink) {
        if (listDrink.size() > 0) {
            binding.rvDrink.setVisibility(View.VISIBLE);
            binding.lnNoData.setVisibility(View.GONE);
        } else {
            binding.rvDrink.setVisibility(View.GONE);
            binding.lnNoData.setVisibility(View.VISIBLE);
        }
    }

}