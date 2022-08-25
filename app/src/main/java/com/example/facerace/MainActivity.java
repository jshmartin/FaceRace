package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavBarInit();
    }




    @SuppressLint("NonConstantResourceId")
    private void bottomNavBarInit() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, new HomeFragment());

        bottomNavigationView.setSelectedItemId(R.id.dashboard_item);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, new ProfileFragment()).commit();
                    return true;
                case R.id.settings_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, new SettingsFragment()).commit();
                    return true;
                case R.id.dashboard_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, new HomeFragment()).commit();
                    return true;
            }


            return false;
        });
    }
}