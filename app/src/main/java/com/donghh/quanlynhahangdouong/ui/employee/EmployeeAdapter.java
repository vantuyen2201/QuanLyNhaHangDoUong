package com.donghh.quanlynhahangdouong.ui.employee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ItemStudentBinding;
import com.donghh.quanlynhahangdouong.ui.entity.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Employee> userArrayList;
    private IStudent iStudentListener;



    public EmployeeAdapter(ArrayList<Employee> userArrayList, IStudent iStudentListener) {
        this.userArrayList = userArrayList;
        this.iStudentListener = iStudentListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Employee user = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        viewHolder.binding.tvStudentName.setText(user.getStudentName());
        viewHolder.binding.tvStudentCode.setText(user.getStudentCode());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudentListener.onClickStudent(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void updateUserList(final ArrayList<Employee> userArrayList) {
        this.userArrayList.clear();
        this.userArrayList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemStudentBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStudentBinding.bind(itemView);


        }
    }

    public  interface IStudent{
        void onClickStudent(Employee student);
    }
}
