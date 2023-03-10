package com.donghh.quanlynhahangdouong.ui.mergebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ItemBookBinding;
import com.donghh.quanlynhahangdouong.databinding.ItemBookMergerBinding;
import com.donghh.quanlynhahangdouong.ui.entity.Book;

import java.util.ArrayList;

public class MergeBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Book> bookList;
    private IBookListener iBookListener;

    private boolean isMutilSelect;



    public MergeBookAdapter(ArrayList<Book> userArrayList, IBookListener iStudentListener, boolean isMutilSelect) {
        this.bookList = userArrayList;
        this.iBookListener = iStudentListener;
        this.isMutilSelect = isMutilSelect;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_merger, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Book book = bookList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        if (book.getListDrink()==null||book.getListDrink().isEmpty()){
            viewHolder.binding.ivBook.setImageResource(R.drawable.icon_book_empty);

        }else {
            viewHolder.binding.ivBook.setImageResource(R.drawable.icon_book_full);
        }

        if (isMutilSelect) {
            if (book.isSelect()) {
                viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_check_circle_24);
            } else {
                viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            }
        }else {
            if (book.isSelect()) {
                viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
            } else {
                viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            }
        }
        viewHolder.binding.tvBook.setText("Bàn "+book.getId());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMutilSelect) {
                    book.setSelect(!book.isSelect());
                    if (book.isSelect()) {
                        viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    } else {
                        viewHolder.binding.icCheck.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                    }
                }else {
                    iBookListener.onClickBook(book);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateUserList(final ArrayList<Book> userArrayList) {
        this.bookList.clear();
        this.bookList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemBookMergerBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookMergerBinding.bind(itemView);


        }
    }

    public  interface IBookListener {
        void onClickBook(Book book);

    }
}
