package com.bca.bakingapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "recipe_table")
public class Recipe implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idRecipe")
    protected String idRecipe;

    String nameRecipe;
    String numberServings;
    String ingredients;
    String steps;

    public Recipe(@NonNull String idRecipe, String nameRecipe, String numberServings, String ingredients, String steps){
        this.idRecipe = idRecipe;
        this.nameRecipe = nameRecipe;
        this.numberServings = numberServings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @NonNull
    public String getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(@NonNull String idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    public String getNumberServings() {
        return numberServings;
    }

    public void setNumberServings(String numberServings) {
        this.numberServings = numberServings;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
