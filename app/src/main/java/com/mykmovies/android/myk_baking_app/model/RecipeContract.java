package com.mykmovies.android.myk_baking_app.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yeswa on 10-02-2018.
 */

public class RecipeContract {
    public static final String AUTHORITY="com.mykmovies.android.myk_baking_app";
    public static final Uri BASE_CONTENT_AUTHORITY=Uri.parse("content://"+AUTHORITY);
    public static final String PATH="recipe_info";
    public static final class RecipeContractInfo implements BaseColumns{

        public static final String RECIPE_INFO_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        /*------------------Database Constants-----------------------------------------*/
        public static final String TABLE_NAME = "recipe_info";
        // "_ID" column in addition to the two below
        public static final String COLUMN_RECIPE_ID ="recipe_id";
        public static final String COLUMN_RECIPE_TITLE = "recipe_title";
        public static final String COLUMN_RECIPE_INGREDIENT="ingredient";
        public static final String COLUMN_RECIPE_QUANTITY="quantity";
        public static final String COLUMN_RECIPE_MEASURE="measure";
        /*------------------------------------------------------------------------------*/
        /*--------------------Recipe DB URI Creation------------------------------------*/
        public static final Uri RECIPE_CONTENT_URI=BASE_CONTENT_AUTHORITY.buildUpon().appendPath(PATH).build();

    }
}
