package com.mykmovies.android.myk_baking_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mykmovies.android.myk_baking_app.adapter.MainRecipeAdapter;
import com.mykmovies.android.myk_baking_app.model.MainRecipeList;
import com.mykmovies.android.myk_baking_app.model.RecipeContract;
import com.mykmovies.android.myk_baking_app.networkutilities.Singleton;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    /**
     * declaring recycleview and recyclevielayoutmanager
     */
    private RecyclerView mainRecipeRecycleView;
    private RecyclerView.LayoutManager mainRecipeLayoutManager;
    private MainRecipeAdapter mainRecipeAdapter;
    private TextView recipeDisplay;
    private Context context;
    private int gridCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * calling RecycleView using findViewByID
         * attaching Recycleview to the LinearLayoutManager
         */
        mainRecipeRecycleView = (RecyclerView) findViewById(R.id.baking_main_recycle_view);
        if(findViewById(R.id.recipe_tab_view)!=null) {
            gridCount=calculateNoOfColumns(getApplicationContext());
            //calculateNoOfColumns(getActivity());
            mainRecipeLayoutManager = new GridLayoutManager(this,gridCount);
            mainRecipeRecycleView.setLayoutManager(mainRecipeLayoutManager);
        }
        else
        {
            mainRecipeLayoutManager = new LinearLayoutManager(this);
            mainRecipeRecycleView.setLayoutManager(mainRecipeLayoutManager);
        }
        mainRecipeRecycleView.setHasFixedSize(true);
        getRecipeInformation();
    }
    private void getRecipeInformation() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RecipeContract.RecipeContractInfo.RECIPE_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        List<MainRecipeList> mainRecipeListInfo = Arrays.asList(gson.fromJson(response, MainRecipeList[].class));
                        mainRecipeAdapter = new MainRecipeAdapter(mainRecipeListInfo,getApplicationContext());
                        mainRecipeRecycleView.setAdapter(mainRecipeAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());

            }
        });
        Singleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 220;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
}
