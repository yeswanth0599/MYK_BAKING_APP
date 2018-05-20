package com.mykmovies.android.myk_baking_app.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mykmovies.android.myk_baking_app.model.RecipeContract;
import com.mykmovies.android.myk_baking_app.provider.db_helper.RecipeDBHelper;

/**
 * Created by yeswa on 06-03-2018.
 */

public class RecipeContentProvider extends ContentProvider{

    public static final int RECIPE_WIDGET = 100;
    public static final int RECIPE_WIDGET_ID = 110;
    private static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH, RECIPE_WIDGET);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH + "/#", RECIPE_WIDGET_ID);
        return uriMatcher;
    }
    RecipeDBHelper recipeDBHelper;
    @Override
    public boolean onCreate() {
        recipeDBHelper=new RecipeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = recipeDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case RECIPE_WIDGET:
                retCursor = sqLiteDatabase.query(RecipeContract.RecipeContractInfo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_WIDGET_ID:
                // Get the task ID from the URI path
                String recipe_id = uri.getPathSegments().get(1);
                retCursor = sqLiteDatabase.query(RecipeContract.RecipeContractInfo.TABLE_NAME,
                        projection,
                        "recipe_id=?",
                        new String[]{recipe_id},
                        null,
                        null,
                        sortOrder);

                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase sqLiteDatabase = recipeDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnInsertUri;

        switch (match) {
            case RECIPE_WIDGET:
                long id = sqLiteDatabase.insert(RecipeContract.RecipeContractInfo.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnInsertUri = ContentUris.withAppendedId(RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("RecipeDetails not inserted" + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("UnKnown Uri" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnInsertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase sqLiteDatabase = recipeDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        // COMPLETED (2) Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case RECIPE_WIDGET:
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = sqLiteDatabase.delete(RecipeContract.RecipeContractInfo.TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED (3) Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
