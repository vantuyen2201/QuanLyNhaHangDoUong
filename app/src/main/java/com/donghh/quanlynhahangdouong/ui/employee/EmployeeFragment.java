package com.donghh.quanlynhahangdouong.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.FragmentEmployeeBinding;
import com.donghh.quanlynhahangdouong.databinding.FragmentHomeBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Employee;
import com.donghh.quanlynhahangdouong.ui.event.UpdateStudentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class EmployeeFragment extends Fragment  implements LifecycleOwner {

    private FragmentEmployeeBinding binding;

    private EmployeeAdapter studentAdapter;

    private DatabaseHandler databaseHandler;

    public static EmployeeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        EmployeeFragment fragment = new EmployeeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        binding = FragmentEmployeeBinding.bind(view);


        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListStudent(databaseHandler.getAllStudents());

        binding.flbAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), AddEmployeeActivity.class);
                startActivity(intent);
            }
        });


        binding.tipStudentCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.tipStudentCode.getText().toString().isEmpty()) {
                    initRecyclerListStudent(databaseHandler.getAllStudents());
                } else {
                    initRecyclerListStudent(databaseHandler.getAllStudentsBKeySearch(binding.tipStudentCode.getText().toString()));

                }

            }
        });
        return view;
    }
    @Subscribe
    public void OnUpdateStudentEvent(UpdateStudentEvent event) {
        studentAdapter.updateUserList(databaseHandler.getAllStudents());
    }
    private void initRecyclerListStudent(ArrayList<Employee> studentList) {
        try{

            studentAdapter = new EmployeeAdapter(studentList, new EmployeeAdapter.IStudent() {
                @Override
                public void onClickStudent(Employee student) {
                    Intent intent= new Intent(getContext(), DetailEmployeeActivity.class);
                    intent.putExtra(DetailEmployeeActivity.DetailStudent,student);
                    startActivity(intent);

                }
            });
            binding.rvStudent.setAdapter(studentAdapter);
        }catch(Exception e){
          e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}