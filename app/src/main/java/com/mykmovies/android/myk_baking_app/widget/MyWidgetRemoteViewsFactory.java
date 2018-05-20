package com.mykmovies.android.myk_baking_app.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.model.RecipeContract;

/**
 * Created by yeswa on 10-03-2018.
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) {
            mCursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        Uri uri = RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_info_provider_widget);
        int recipeIngredientIndex = mCursor.getColumnIndex(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_INGREDIENT);
        int recipeQuantitiyIndex = mCursor.getColumnIndex(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_QUANTITY);
        int recipeMeasureIndex=mCursor.getColumnIndex(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_MEASURE);
        String recipeIngredient = mCursor.getString(recipeIngredientIndex);
        String recipeQuantity = mCursor.getString(recipeQuantitiyIndex);
        String recipeMeasure=mCursor.getString(recipeMeasureIndex);
        rv.setTextViewText(R.id.recipe_ingredient_name, recipeIngredient);
        rv.setTextViewText(R.id.recipe_ingredient_quantity, recipeQuantity);
        rv.setTextViewText(R.id.recipe_ingredient_measure, recipeMeasure);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
