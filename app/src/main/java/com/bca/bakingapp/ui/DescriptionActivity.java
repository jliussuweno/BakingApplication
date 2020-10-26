package com.bca.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bca.bakingapp.R;
import com.bca.bakingapp.adapter.IngredientsListAdapter;
import com.bca.bakingapp.adapter.StepListAdapter;
import com.bca.bakingapp.callback.StepCallback;
import com.bca.bakingapp.model.Ingredient;
import com.bca.bakingapp.model.Recipe;
import com.bca.bakingapp.model.Step;
import com.bca.bakingapp.viewmodel.DescriptionViewModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity implements StepCallback, Serializable, Player.EventListener {
    RecyclerView recyclerViewIngredient;
    RecyclerView recyclerViewStep;
    TextView textViewName;
    TextView textViewServings;
    ToggleButton favorite_button;
    private SimpleExoPlayer simpleExoplayer;
    PlayerView playerView;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        getSupportActionBar().setTitle(R.string.description_menu);
        textViewName = findViewById(R.id.textViewName);
        textViewServings = findViewById(R.id.textViewServings);
        favorite_button = findViewById(R.id.toggleButton);

        simpleExoplayer = new SimpleExoPlayer.Builder(this).build();
        playerView = findViewById(R.id.exoplayerView);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
        String id = recipe.getIdRecipe();
        String name = recipe.getNameRecipe();
        String servings = recipe.getNumberServings();
        String ingredients = recipe.getIngredients();
        String steps = recipe.getSteps();

        textViewName.setText(name);
        textViewServings.setText("Number of servings : " + servings);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewIngredient = findViewById(R.id.recyclerViewIngredients);
        IngredientsListAdapter adapterIngre = new IngredientsListAdapter(this);
        recyclerViewIngredient.setLayoutManager(llm);
        recyclerViewIngredient.setAdapter(adapterIngre);

        recyclerViewStep = findViewById(R.id.recyclerViewSteps);
        StepListAdapter stepListAdapter = new StepListAdapter(this);
        stepListAdapter.setCallback(this);
        recyclerViewStep.setLayoutManager(llm2);
        recyclerViewStep.setAdapter(stepListAdapter);


        DescriptionViewModel descriptionViewModel = new ViewModelProvider(this).get(DescriptionViewModel.class);
        descriptionViewModel.setListIngredients(ingredients);
        descriptionViewModel.getListIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                adapterIngre.setmIngredients(ingredients);
            }
        });

        descriptionViewModel.setListSteps(steps);
        descriptionViewModel.getListSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                stepListAdapter.setmSteps(steps);
            }
        });

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        descriptionViewModel.checkFavorite(recipe);
        favorite_button.setChecked(descriptionViewModel.isFavorite);
        favorite_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);
                if (isChecked == true) {
                    descriptionViewModel.insertFavoriteRecipe(recipe);
                } else {
                    descriptionViewModel.deleteFavoriteRecipe(recipe);
                }
            }
        });

    }


    @Override
    public void stepPressed(Step step) {
        simpleExoplayer.stop();
        if (step.getLinkStep().isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            playerView.setPlayer(simpleExoplayer);
            playerView.setVisibility(View.GONE);
            Toast.makeText(this, "Step " + step.getDescription() + " ini tidak memiliki Video", Toast.LENGTH_SHORT).show();
        } else {
            playerView.setVisibility(View.VISIBLE);
            playerView.setPlayer(simpleExoplayer);
            playerView.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse(step.getLinkStep());
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            simpleExoplayer.setMediaItem(mediaItem);
            simpleExoplayer.prepare();
            simpleExoplayer.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer(simpleExoplayer);

    }

    @Override
    protected void onStop() {
        super.onStop();
        pausePlayer(simpleExoplayer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseExoPlayer(simpleExoplayer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer(simpleExoplayer);
    }

    public static void startPlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(true);
        }
    }

    public static void pausePlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    public static void releaseExoPlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        if (playerView.getVisibility() == View.VISIBLE){
            simpleExoplayer.stop();
            playerView.setVisibility(View.GONE);
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }
}