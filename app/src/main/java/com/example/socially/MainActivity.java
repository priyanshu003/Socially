package com.example.socially;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.socially.fragments.FragmentCreatepost;
import com.example.socially.fragments.FragmentProfile;
import com.example.socially.fragments.FragmentSearch;
import com.example.socially.fragments.FragmentSetting;
import com.example.socially.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragment(new HomeFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setItemBackground(null);
        navigation.getMenu().getItem(2).isEnabled();
        FloatingActionButton FavNewPost = findViewById(R.id.fabNewPost);
        FavNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragmentContainer, new FragmentCreatepost())
                        .commit();
            }
        });

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                break;

            case R.id.search:
                fragment = new FragmentSearch();
                break;

            case R.id.profile:
                fragment = new FragmentProfile();
                break;

            case R.id.setting:
                fragment = new FragmentSetting();
                break;
//            case R.id.globalActionToCreatePostFragment:
//                fragment = new FragmentCreatepost();
//                break;

        }

        return loadFragment(fragment);
    }

}