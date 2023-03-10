package com.donghh.quanlynhahangdouong.ui.drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.donghh.quanlynhahangdouong.R;
import com.donghh.quanlynhahangdouong.databinding.FragmentDashboardBinding;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;

public class DrinkFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DatabaseHandler databaseHandler;
    private DrinkAdapter drinkAdapter;


    public static DrinkFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DrinkFragment fragment = new DrinkFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        binding = FragmentDashboardBinding.bind(view);

        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListDrink();

        initListener();

        return view;
    }

    private void initListener() {
        try{
            binding.flbAddDrink.setOnClickListener(v -> {
                UpdateDrinkDialog userInfoDialog = UpdateDrinkDialog.newInstance(null, new UpdateDrinkDialog.IDrink(){
                    @Override
                    public void updateDrink() {
                        initRecyclerListDrink();
                    }
                });
                userInfoDialog.show(getActivity().getSupportFragmentManager(), null);
            });
        }catch(Exception e){
          e.printStackTrace();
        }
    }

    private void initRecyclerListDrink() {
        try{
//            binding.rvData.setLayoutManager(new GridLayoutManager(getContext(), 2));

            drinkAdapter = new DrinkAdapter(databaseHandler.getAllDrink(), new DrinkAdapter.IDrinkListener() {


                @Override
                public void onSelectDrink(Drink drink) {
                    UpdateDrinkDialog userInfoDialog = UpdateDrinkDialog.newInstance(drink, new UpdateDrinkDialog.IDrink(){
                        @Override
                        public void updateDrink() {
                            initRecyclerListDrink();
                        }
                    });
                    userInfoDialog.show(getActivity().getSupportFragmentManager(), null);
                }
            });
            binding.rvData.setAdapter(drinkAdapter);
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