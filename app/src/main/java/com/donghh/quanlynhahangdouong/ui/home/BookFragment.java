package com.donghh.quanlynhahangdouong.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.FragmentHomeBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.detailbook.DetailBookActivity;
import com.donghh.quanlynhahangdouong.ui.entity.Book;
import com.donghh.quanlynhahangdouong.ui.event.UpdateBookEvent;
import com.donghh.quanlynhahangdouong.ui.mergebook.MergeBookActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BookFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseHandler databaseHandler;

    private BookAdapter bookAdapter;

    public static BookFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BookFragment fragment = new BookFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);

        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListBook();

        initListener();
        return view;
    }

    private void initListener() {
        binding.flbAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.addBook(new Book());
                initRecyclerListBook();
                binding.rvBook.scrollToPosition(databaseHandler.getAllBook().size()-1);
            }
        });

        binding.flbMerger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MergeBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerListBook() {
        try{
            binding.rvBook.setLayoutManager(new GridLayoutManager(getContext(), 2));

            bookAdapter = new BookAdapter(databaseHandler.getAllBook(), new BookAdapter.IBookListener() {
                @Override
                public void onClickBook(Book book) {
                    Intent intent= new Intent(getContext(), DetailBookActivity.class);
                    intent.putExtra(DetailBookActivity.DetailBook,book);
                    startActivity(intent);
                }

            });
            binding.rvBook.setAdapter(bookAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void OnEvent(UpdateBookEvent event) {
      initRecyclerListBook();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}