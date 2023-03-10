package com.donghh.quanlynhahangdouong.ui.employee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ActivityAddEmployeeBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Employee;
import com.donghh.quanlynhahangdouong.ui.event.UpdateStudentEvent;


import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    ActivityAddEmployeeBinding binding;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        initListener();


    }


    private void initListener() {
        try {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel();
                }
            };
            binding.tipStudentBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(AddEmployeeActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            binding.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!binding.tipStudentCode.getText().toString().isEmpty()){
                        if (!databaseHandler.IsExistStudentByStudentCode(binding.tipStudentCode.getText().toString())){
                            databaseHandler.addStudent(new Employee(binding.tipStudentCode.getText().toString(),binding.tipStudentName.getText().toString(),
                                    binding.tipStudentBirthday.getText().toString(),
                                    binding.tipStudentPhone.getText().toString()));
                            binding.tipStudentCode.getText().clear();
                            binding.tipStudentName.getText().clear();
                            binding.tipStudentPhone.getText().clear();
                            Toast.makeText(AddEmployeeActivity.this, getString(R.string.add_student_success), Toast.LENGTH_SHORT).show();

                            EventBus.getDefault().post(new UpdateStudentEvent());

                        }else {
                            Toast.makeText(AddEmployeeActivity.this, getString(R.string.exist_student), Toast.LENGTH_SHORT).show();

                        }

                    }                }
            });

            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.tipStudentBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }

}
