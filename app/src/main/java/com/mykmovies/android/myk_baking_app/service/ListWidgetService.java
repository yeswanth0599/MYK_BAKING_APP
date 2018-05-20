package com.mykmovies.android.myk_baking_app.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.mykmovies.android.myk_baking_app.widget.MyWidgetRemoteViewsFactory;

public class ListWidgetService extends RemoteViewsService {
    private static final String TAG = "ListWidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: " + "Service called");
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
