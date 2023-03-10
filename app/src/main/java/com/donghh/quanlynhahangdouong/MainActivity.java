package com.donghh.quanlynhahangdouong;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.donghh.quanlynhahangdouong.ui.Common;
import com.donghh.quanlynhahangdouong.ui.SplashActivity;
import com.donghh.quanlynhahangdouong.ui.ViewPagerAdapter;
import com.donghh.quanlynhahangdouong.ui.database.DatabaseHandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.donghh.quanlynhahangdouong.databinding.ActivityMainBinding;
import com.donghh.quanlynhahangdouong.ui.drink.DrinkFragment;
import com.donghh.quanlynhahangdouong.ui.employee.EmployeeFragment;
import com.donghh.quanlynhahangdouong.ui.entity.Book;
import com.donghh.quanlynhahangdouong.ui.entity.Drink;
import com.donghh.quanlynhahangdouong.ui.home.BookFragment;
import com.donghh.quanlynhahangdouong.ui.setting.SettingFragment;
import com.donghh.quanlynhahangdouong.ui.statistic.StatisticFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHandler databaseHandler;

    private static final int MENU_ITEM_ID_ONE =1;
    private static final int MENU_ITEM_ID_TWO =2;
    private static final int MENU_ITEM_ID_THREE =3;
    private static final int MENU_ITEM_ID_FOUR =4;
    private static final int MENU_ITEM_ID_FIVE =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null,DatabaseHandler.DATABASE_VERSION);
        if (!Common.getBoolean(MainActivity.this, Common.CREATE_DATABASE)) {
            addDatabase();
            Common.putBoolean(MainActivity.this, Common.CREATE_DATABASE, true);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//        NavHostFragment navHomeFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_home);
//        NavHostFragment navDrinkFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_dashboard);
//        NavHostFragment navStatisticFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_notifications);

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.vpData.setCurrentItem(item.getItemId()-1);

                return true;
            }

        });
        Menu menu = binding.navView.getMenu();
        ArrayList<Fragment> listFragment = new ArrayList<>();
        if (Common.getBoolean(MainActivity.this, Common.IS_MANAGER)) {
            listFragment.add(BookFragment.newInstance());
            listFragment.add(DrinkFragment.newInstance());
            listFragment.add(EmployeeFragment.newInstance());
            listFragment.add(StatisticFragment.newInstance());
            listFragment.add(SettingFragment.newInstance());

            menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.title_book))
                    .setIcon(R.drawable.ic_home_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_TWO, Menu.NONE, getString(R.string.title_menu))
                    .setIcon(R.drawable.ic_baseline_restaurant_menu_24);
            menu.add(Menu.NONE, MENU_ITEM_ID_THREE, Menu.NONE, getString(R.string.employee))
                    .setIcon(R.drawable.ic_baseline_person_pin_24);
            menu.add(Menu.NONE, MENU_ITEM_ID_FOUR, Menu.NONE, getString(R.string.title_statistical))
                    .setIcon(R.drawable.ic_baseline_edit_note_24);
            menu.add(Menu.NONE, MENU_ITEM_ID_FIVE, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);

        }else {
            listFragment.add(BookFragment.newInstance());
            listFragment.add(SettingFragment.newInstance());
            menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.title_book))
                    .setIcon(R.drawable.ic_home_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_TWO, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);
        }

        ViewPagerAdapter adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        binding.vpData.setOffscreenPageLimit(listFragment.size());
        binding.vpData.setAdapter(adapterViewPager);
    }


    private void addDatabase() {
        try{
            //10 bàn
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            databaseHandler.addBook(new Book());
            //đồ uống
            databaseHandler.addDrink(new Drink("Trà đào cam sả",0,40000));
            databaseHandler.addDrink(new Drink("Trà sen vàng",0,50000));
            databaseHandler.addDrink(new Drink("Trà vải",0,30000));
            databaseHandler.addDrink(new Drink("Trà long nhãn hạt chia",0,40000));
            databaseHandler.addDrink(new Drink("Trà Sữa Mắc Ca Trân Châu",0,50000));
            databaseHandler.addDrink(new Drink("Hồng Trà Latte Macchiato",0,30000));
            databaseHandler.addDrink(new Drink("Hồng Trà Sữa Nóng",0,50000));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}