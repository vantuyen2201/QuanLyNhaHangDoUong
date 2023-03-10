package com.donghh.quanlynhahangdouong.ui.mergebook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.donghh.quanlynhahangdouong.databinding.ActivityMergeBookBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Bill;
import com.donghh.quanlynhahangdouong.ui.entity.Book;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;
import com.donghh.quanlynhahangdouong.ui.event.UpdateBookEvent;
import com.donghh.quanlynhahangdouong.ui.order.DetailOrderAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * chuyển/ gộp bàn
 */
public class MergeBookActivity extends AppCompatActivity {

    private ActivityMergeBookBinding binding;

    private DetailOrderAdapter drinkBookAdapter;

    private Bill bill;
    private DatabaseHandler databaseHandler;

    private MergeBookAdapter bookAdapterFrom;
    private MergeBookAdapter bookAdapterTo;


    private ArrayList<Book> listBookFrom; //danh sách chọn bàn để gộp/chuyển
    private ArrayList<Book> listBookTo; //bàn cần gộp/ chuyển sau đó
    private Book bookTo; //bàn cần gộp/ chuyển sau đó

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMergeBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();


        initRecyclerListBook();

        initListener();
    }

    private void initData() {
        try {
            databaseHandler = new DatabaseHandler(MergeBookActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
            bill = databaseHandler.getBillLast();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

                ArrayList<Drink> listDrink = new ArrayList<>();//danh sách đồ uống của tất cả bàn đang chọn

                //Lấy danh sách bàn đang chọn
                for (Book book : listBookFrom) {
                    if (book.isSelect()&&book.getListDrink() != null) {
                        Type listType = new TypeToken<ArrayList<Drink>>() {
                        }.getType();
                        ArrayList<Drink> listDrinkItem = new Gson().fromJson(book.getListDrink(), listType);
                        if (listDrinkItem != null) {
                            listDrink.addAll(listDrinkItem);
                        }

                        databaseHandler.updateBookById(book.getId(), "");

                    }

                }

                //bàn cũ
                if (bookTo.getListDrink() != null) {
                    Type listType = new TypeToken<ArrayList<Drink>>() {
                    }.getType();
                    ArrayList<Drink> listDrinkItem = new Gson().fromJson(bookTo.getListDrink(), listType);
                    if (listDrinkItem != null) {
                        listDrink.addAll(listDrinkItem);
                    }

                }

//                bookTo.setListDrink(new Gson().toJson(listDrink));
                if (listDrink.size() > 0) {
                    databaseHandler.updateBookById(bookTo.getId(), new Gson().toJson(listDrink));
                } else {
                    databaseHandler.updateBookById(bookTo.getId(), "");

                }

                EventBus.getDefault().post(new UpdateBookEvent());
                finish();
            }
        });




    }



    private void initRecyclerListBook() {
        try {

            listBookFrom = databaseHandler.getAllBook();
            listBookTo = databaseHandler.getAllBook();

            binding.rvBookFrom.setLayoutManager(new GridLayoutManager(MergeBookActivity.this, 4));
            bookAdapterFrom = new MergeBookAdapter(listBookFrom, new MergeBookAdapter.IBookListener() {
                @Override
                public void onClickBook(Book book) {

                }

            },true);
            binding.rvBookFrom.setAdapter(bookAdapterFrom);


            binding.rvBookTo.setLayoutManager(new GridLayoutManager(MergeBookActivity.this, 4));
            bookAdapterTo = new MergeBookAdapter(listBookTo, new MergeBookAdapter.IBookListener() {
                @Override
                public void onClickBook(Book book) {
                    bookTo = book;
                    for (Book book1 : listBookTo) {
                        if (book.getId()==book1.getId()){
                            book1.setSelect(true);
                        }else {
                            book1.setSelect(false);
                        }
                    }

                    bookAdapterTo.notifyDataSetChanged();

                }

            },false);
            binding.rvBookTo.setAdapter(bookAdapterTo);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}