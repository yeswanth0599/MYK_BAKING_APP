package com.mykmovies.android.myk_baking_app.model;

import java.io.Serializable;

/**
 * Created by yeswa on 10-02-2018.
 */

public class IngredientsList implements Serializable{
    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
