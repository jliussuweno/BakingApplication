package com.bca.bakingapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bca.bakingapp.database.RecipeDao;
import com.bca.bakingapp.database.RecipeRoomDatabase;
import com.bca.bakingapp.model.Ingredient;
import com.bca.bakingapp.model.Recipe;
import com.bca.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescriptionViewModel extends AndroidViewModel {

    private MutableLiveData<List<Ingredient>> listIngredients;
    private MutableLiveData<List<Step>> listSteps;
    public Boolean isFavorite;
    Context context;
    private RecipeDao mRecipeDao;


    public DescriptionViewModel(@NonNull Application application) {
        super(application);
        listIngredients = new MutableLiveData<>();
        listSteps = new MutableLiveData<>();
        context = application.getApplicationContext();
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(context);
        mRecipeDao = db.recipeDao();
    }

    public MutableLiveData<List<Ingredient>> getListIngredients() {
        return listIngredients;
    }

    public MutableLiveData<List<Step>> getListSteps() {
        return listSteps;
    }

    public void setListIngredients(String ingredients){

        List<Ingredient> arrIngredients = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(ingredients);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String quantity = jsonObject.getString("quantity");
                String measure = jsonObject.getString("measure");
                String ingre = jsonObject.getString("ingredient");
                arrIngredients.add(new Ingredient(quantity,measure,ingre));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listIngredients.postValue(arrIngredients);

    }

    public void setListSteps(String steps){

        List<Step> arrSteps = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(steps);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String shortDescription = jsonObject.getString("shortDescription");
                String description = jsonObject.getString("description");
                String videoURL = jsonObject.getString("videoURL");
                arrSteps.add(new Step(id, shortDescription, description, videoURL));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listSteps.postValue(arrSteps);
    }

    public void checkFavorite(Recipe recipe){

        int count = mRecipeDao.checkRecipe(recipe.getIdRecipe());

        if (count == 1){
            isFavorite = true;
        } else {
            isFavorite = false;
        }
    }

    public void insertFavoriteRecipe(Recipe recipe){
        mRecipeDao.insertRecipe(recipe);
    }


    public void deleteFavoriteRecipe(Recipe recipe) {
        mRecipeDao.deleteRecipe(recipe.getIdRecipe());
    }
}
