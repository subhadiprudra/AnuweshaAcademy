package com.easylife.aunweshaacademy.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.easylife.aunweshaacademy.R;
import com.easylife.aunweshaacademy.fragment.Account;
import com.easylife.aunweshaacademy.fragment.AllCourses;
import com.easylife.aunweshaacademy.fragment.MyCourses;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

public class Menu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    JSONArray jsonArray=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.darkLite));
            //getWindow().setStatusBarColor(getResources().getColor(R.color.loginBack));
        }





        loadFragment(new AllCourses(this));

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottomnavigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.all_course:
                fragment = new AllCourses(this);
                break;

            case R.id.my_course:
                fragment = new MyCourses(this);
                break;

            case R.id.account:
                fragment = new Account();
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

    }
}