package com.bca.bakingapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bca.bakingapp.database.RecipeRepository;
import com.bca.bakingapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mRecipes;
    private MutableLiveData<List<Recipe>> mRecipesNotFavorite;
    private Context context;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mRepository = new RecipeRepository(application);
        mRecipesNotFavorite = new MutableLiveData<>();
        mRecipes = mRepository.getmFavoriteRecipes();
    }

    public LiveData<List<Recipe>> getmRecipes(){
        return mRecipes;
    }

    public MutableLiveData<List<Recipe>> getmRecipesNotFavorite() {
        return mRecipesNotFavorite;
    }

    public void insert(Recipe recipe){
        mRepository.insert(recipe);
    }

    public void initDataRecipe(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    List<Recipe> recipes = new ArrayList<>();

                    try {
                        JSONArray object = new JSONArray(response);

                        for(int i = 0; i < object.length(); i++){
                            JSONObject jsonObject = object.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String servings = jsonObject.getString("servings");
                            String ingredients = jsonObject.getString("ingredients");
                            String steps = jsonObject.getString("steps");

                            recipes.add(new Recipe(id,name,servings,ingredients,steps));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mRecipesNotFavorite.postValue(recipes);

                }, error -> Log.d("TAG","That didn't work!"));

        queue.add(stringRequest);
    }

    public void initFavoriteData(){
        mRecipes = mRepository.getmFavoriteRecipes();
    }

}
