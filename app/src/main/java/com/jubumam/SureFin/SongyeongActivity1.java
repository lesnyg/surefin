package com.jubumam.SureFin;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SongyeongActivity1 extends BaseActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BeforeMealFragment beforeFragment = new BeforeMealFragment();
    private AfterMealFragment afterFragment = new AfterMealFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songyeong1);

        activateToolbar();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_main, beforeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.action_before:
                    transaction.replace(R.id.fragment_main, beforeFragment).commitAllowingStateLoss();

                    break;
                case R.id.action_after:
                    transaction.replace(R.id.fragment_main, afterFragment).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }
}
