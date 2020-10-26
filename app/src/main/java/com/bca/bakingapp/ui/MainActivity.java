package com.bca.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.bca.bakingapp.R;
import com.bca.bakingapp.adapter.ViewPagerAdapter;
import com.bca.bakingapp.callback.RecipeCallback;
import com.bca.bakingapp.model.Recipe;
import com.bca.bakingapp.viewmodel.RecipeViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements RecipeCallback, Serializable{

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void recipePressed(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("recipe", (Serializable) recipe);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}