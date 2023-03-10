package com.donghh.quanlynhahangdouong.ui.detailbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ItemDrinkBookBinding;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DrinkBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Drink> drinkList;


    public ArrayList<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(ArrayList<Drink> drinkList) {
        this.drinkList = drinkList;
    }

    public DrinkBookAdapter(ArrayList<Drink> drinkList) {
        this.drinkList = drinkList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink_book, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Drink drink = drinkList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        viewHolder.binding.tvDrinkCode.setText("Mã: "+drink.getId());
        viewHolder.binding.tvDrinkName.setText(drink.getDrinkName());

        NumberFormat formatter = new DecimalFormat("#,###");
        viewHolder.binding.tvPrice.setText(formatter.format(drink.getPrice())+"đ");
        viewHolder.binding.tvAmount.setText(String.valueOf(drink.getAmount()));

        viewHolder.binding.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drink.getAmount()>0){
                    drink.setAmount(drink.getAmount()-1);
                    viewHolder.binding.tvAmount.setText(String.valueOf(drink.getAmount()));

                }
            }
        });

        viewHolder.binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drink.setAmount(drink.getAmount()+1);
                    viewHolder.binding.tvAmount.setText(String.valueOf(drink.getAmount()));

            }
        });
    }

    @Override
    public int getItemCount() {
        if (drinkList !=null) {
            return drinkList.size();
        }else {
            return 0;
        }
    }

    public void updateDrinkList(final ArrayList<Drink> userArrayList) {
        try{
            this.drinkList.clear();
            this.drinkList = userArrayList;
            notifyDataSetChanged();
        }catch(Exception e){
          e.printStackTrace();
        }

    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemDrinkBookBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDrinkBookBinding.bind(itemView);


        }
    }


}
