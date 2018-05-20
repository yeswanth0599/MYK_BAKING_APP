package com.mykmovies.android.myk_baking_app.model;

import java.util.ArrayList;

/**
 * Created by yeswa on 10-02-2018.
 */

public class MainRecipeList {
    private int id;
    private String name;
    private ArrayList<IngredientsList> ingredients;
    private ArrayList<RecipeStepsList> steps;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<IngredientsList> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStepsList> getSteps() {
        return steps;
    }
}
