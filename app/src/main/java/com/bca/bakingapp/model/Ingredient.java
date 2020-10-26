package com.bca.bakingapp.model;

public class Ingredient {

    String quantity;
    String measure;
    String ingre;

    public Ingredient(String quantity, String measure, String ingre){
        this.quantity = quantity;
        this.measure = measure;
        this.ingre = ingre;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngre() {
        return ingre;
    }

    public void setIngre(String ingre) {
        this.ingre = ingre;
    }

}
