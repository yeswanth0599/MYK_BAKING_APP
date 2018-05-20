package com.mykmovies.android.myk_baking_app.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.RemoteViews;

import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.model.RecipeContract;
import com.mykmovies.android.myk_baking_app.service.ListWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeInfoProviderWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        String recipeTile=null;
        String emptyRecipeTitle="MYK RECiPE WORLD";
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(

                    context.getPackageName(),
                    R.layout.widget_lrecipe_listview

            );

            long identityToken = Binder.clearCallingIdentity();
            Uri uri = RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(uri,
                    null,
                    null,
                    null,
                    null);

            Binder.restoreCallingIdentity(identityToken);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int recipeTitleIndex = cursor.getColumnIndex(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_TITLE);
                recipeTile=cursor.getString(recipeTitleIndex);
                views.setTextViewText(R.id.recipe_widget_title, recipeTile);
                cursor.close();
            }
            else
            {
                views.setTextViewText(R.id.recipe_widget_title, emptyRecipeTitle);
            }

            Intent intent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(R.id.recipe_listview, intent);
            views.setEmptyView(R.id.recipe_listview, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeInfoProviderWidget.class));
        context.sendBroadcast(intent);
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        String recipeTile1=null;
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            boolean dataUpdateCheck;

            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeInfoProviderWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.recipe_listview);
            onUpdate(context,mgr,mgr.getAppWidgetIds(cn));
        }
        super.onReceive(context, intent);
    }
}

