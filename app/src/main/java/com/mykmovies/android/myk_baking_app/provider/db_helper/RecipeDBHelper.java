package com.mykmovies.android.myk_baking_app.provider.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mykmovies.android.myk_baking_app.model.RecipeContract;

/**
 * Created by yeswa on 06-03-2018.
 */

public class RecipeDBHelper extends SQLiteOpenHelper{
    // The name of the database
    private static final String DATABASE_NAME = "MYK_RECIPE_DB_CHECK2.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    public RecipeDBHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "  + RecipeContract.RecipeContractInfo.TABLE_NAME + " (" +
                RecipeContract.RecipeContractInfo.COLUMN_RECIPE_ID + " TEXT NOT NULL, " +
                RecipeContract.RecipeContractInfo.COLUMN_RECIPE_TITLE + " TEXT NOT NULL, " +
                RecipeContract.RecipeContractInfo.COLUMN_RECIPE_INGREDIENT + " TEXT NOT NULL, " +
                RecipeContract.RecipeContractInfo.COLUMN_RECIPE_QUANTITY + " TEXT NOT NULL, " +
                RecipeContract.RecipeContractInfo.COLUMN_RECIPE_MEASURE + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeContractInfo.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
