package com.mykmovies.android.myk_baking_app.networkutilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yeswa on 10-02-2018.
 */

public class Singleton {
    private static Singleton mInstance;
    private static Context mContext;
    private RequestQueue requestQueue;

    private Singleton(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return requestQueue;
    }

    public static synchronized Singleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Singleton(context);
        }
        return mInstance;
    }
    public<T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }

}
