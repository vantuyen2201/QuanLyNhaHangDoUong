package com.donghh.quanlynhahangdouong.ui.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ActivityOrderBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Bill;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;

    private DetailOrderAdapter drinkBookAdapter;

    private Bill bill;
    private DatabaseHandler databaseHandler;


    private Bitmap bitmap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();


        initRecyclerListDrink();

        initListener();
    }

    private void initData() {
        try {
            databaseHandler = new DatabaseHandler(OrderActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
            bill = databaseHandler.getBillLast();

            NumberFormat formatter = new DecimalFormat("#,###");
            binding.tvTotalMoney.setText(formatter.format(bill.getTotalMoney()) + "đ");
            binding.tvOrderId.setText(getString(R.string.id_bill, String.valueOf(bill.getId())));
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


        binding.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ActivityCompat.requestPermissions(OrderActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    bitmap = LoadBitmap(binding.srData, binding.srData.getWidth(), binding.srData.getHeight());
                    createPdf();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(OrderActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void createPdf() {
        try {


            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            //  Display display = wm.getDefaultDisplay();
            DisplayMetrics displaymetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            float hight = displaymetrics.heightPixels;
            float width = displaymetrics.widthPixels;

            int convertHighet = (int) hight, convertWidth = (int) width;

            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            canvas.drawPaint(paint);

            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);

            // write the document content
            String targetPdf = "/sdcard/page.pdf";
            File filePath;
            filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }////////////////////

            // close the document
            document.close();
            Toast.makeText(this, "Xuất hóa đơn thành công", Toast.LENGTH_SHORT).show();

            openPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void openPdf() {
        try {
            File file = new File("/sdcard/page.pdf");
            if (file.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    private void initRecyclerListDrink() {
        try {
            binding.rvDrink.setLayoutManager(new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false));

            ArrayList<Drink> listDrink = new ArrayList<>();
            if (bill.getListDrink() != null) {
                Type listType = new TypeToken<ArrayList<Drink>>() {
                }.getType();
                listDrink = new Gson().fromJson(bill.getListDrink(), listType);
                if (listDrink == null) {
                    listDrink = new ArrayList<>();
                }

            }
            drinkBookAdapter = new DetailOrderAdapter(listDrink);
            binding.rvDrink.setAdapter(drinkBookAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}