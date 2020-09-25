package com.beetleware.task.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beetleware.bottomnavbar.BottomNavBar;
import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainBottomActivity extends BaseActivity<MainViewModel> {

    @BindView(R.id.bottom_nav_view)
    BottomNavBar bottomNavBar;
    @BindView(R.id.title)
    TextView title;

    @Override
    public int getLayout() {
        return R.layout.activity_main_bottom;
    }

    @Override
    public void initActivity(Bundle savedInstanceState) {
        hideToolbar();
        BottomNavigationView navView = bottomNavBar.findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navHome, R.id.navSearch, R.id.navProfile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.navHome:
                        title.setText(R.string.label_home);
                        break;
                    case R.id.navProfile:
                        title.setText(R.string.label_profile);
                        break;
                    case R.id.navSearch:
                        title.setText(R.string.label_search);
                        break;
                }
            }
        });
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }
}