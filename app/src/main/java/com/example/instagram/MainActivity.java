package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import  androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.instagram.Fragments.HomeFragment;
import com.example.instagram.Fragments.NotificationFragment;
import com.example.instagram.Fragments.ProfileFragment;
import com.example.instagram.Fragments.SearchFragment;
import com.example.instagram.Fragments.addFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                selectorFragment = new HomeFragment();
                            case R.id.nav_notification:
                                selectorFragment = new NotificationFragment();
                            case R.id.nav_profile:
                                selectorFragment = new ProfileFragment();
                            case R.id.nav_search:
                                selectorFragment = new SearchFragment();
                            case R.id.add_fragment:
                                selectorFragment = new addFragment();
                                startActivity(new Intent(MainActivity.this,PostActivity.class));
                                break;


                        }
                        if (selectorFragment != null)
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                        return true;
                    }

                });

getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
    }
}
