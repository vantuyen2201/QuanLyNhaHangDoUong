package com.donghh.quanlynhahangdouong.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.ItemBookBinding;
import com.donghh.quanlynhahangdouong.ui.entity.Book;


import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Book> bookList;
    private IBookListener iBookListener;



    public BookAdapter(ArrayList<Book> userArrayList, IBookListener iStudentListener) {
        this.bookList = userArrayList;
        this.iBookListener = iStudentListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
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
        viewHolder.binding.tvBook.setText("Bàn "+book.getId());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBookListener.onClickBook(book);
            }
        });
//        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                iBookListener.onLongClickBook(book);
//                return false;
//            }
//        });
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
        ItemBookBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookBinding.bind(itemView);


        }
    }

    public  interface IBookListener {
        void onClickBook(Book book);

    }
}
