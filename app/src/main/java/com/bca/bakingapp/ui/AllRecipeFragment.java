package com.bca.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.bakingapp.R;
import com.bca.bakingapp.adapter.RecipeListAdapter;
import com.bca.bakingapp.callback.RecipeCallback;
import com.bca.bakingapp.model.Recipe;
import com.bca.bakingapp.viewmodel.RecipeViewModel;

import java.io.Serializable;
import java.util.List;

public class AllRecipeFragment extends Fragment implements RecipeCallback {

    private RecipeViewModel mRecipeViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_allrecipe, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(getContext());
        adapter.setCallback((RecipeCallback) getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.initDataRecipe();
        mRecipeViewModel.getmRecipesNotFavorite().observe((LifecycleOwner) getContext(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setmRecipes(recipes);
            }
        });

    }

    @Override
    public void recipePressed(Recipe recipe) {
        Intent intent = new Intent(getContext(), DescriptionActivity.class);
        intent.putExtra("recipe", (Serializable) recipe);
        startActivity(intent);
    }
}