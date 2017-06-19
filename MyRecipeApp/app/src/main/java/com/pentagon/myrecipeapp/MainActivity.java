package com.pentagon.myrecipeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pentagon.myrecipeapp.details.RecipeDetailsFragment;
import com.pentagon.myrecipeapp.main.MainActivityFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new MainActivityFragment()).commit();

    }
    @Override
    public void onBackPressed() {
        Fragment fragment =  getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof MainActivityFragment){
            ((MainActivityFragment)fragment).onBackPressed();
        }else if(fragment instanceof RecipeDetailsFragment){
            ((RecipeDetailsFragment)fragment).onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
}
