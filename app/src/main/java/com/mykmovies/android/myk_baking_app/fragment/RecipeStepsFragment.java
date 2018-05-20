package com.mykmovies.android.myk_baking_app.fragment;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.adapter.DescribeRecipeStepsAdapter;
import com.mykmovies.android.myk_baking_app.adapter.RecipeStepsAdapter;
import com.mykmovies.android.myk_baking_app.model.IngredientsList;
import com.mykmovies.android.myk_baking_app.model.RecipeContract;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;
import com.mykmovies.android.myk_baking_app.widget.RecipeInfoProviderWidget;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class RecipeStepsFragment extends Fragment {


    public static final String INGREDIENT_LIST = "ingredient_list";
    public static final String RECIPESTEPS_LIST = "recipe_step_list";
    public static final String SHARED_PREF_RECIPE_TITLE="recipe title";

    private TextView recipeStepsName;
    private final String TAG = "RecipeStepsFragment";
    private RecyclerView recipeStepsRecycleView;
    private RecyclerView.LayoutManager recipeStepsLayoutManager;
    private RecipeStepsAdapter recipeStepsAdapter;
    private DescribeRecipeStepsAdapter describeRecipeStepsAdapter;
    ArrayList<IngredientsList> arrayListObject;
    ArrayList<RecipeStepsList> arrayRecipeListObject;
    private String recipeTitle;
    private int recipeID;
    ActionBar actionBar;


    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            arrayListObject = (ArrayList<IngredientsList>) savedInstanceState.getSerializable(INGREDIENT_LIST);
            arrayRecipeListObject = (ArrayList<RecipeStepsList>) savedInstanceState.getSerializable(RECIPESTEPS_LIST);
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        recipeStepsRecycleView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_info_recycle_view);
        recipeStepsLayoutManager = new LinearLayoutManager(getContext());
        recipeStepsRecycleView.setLayoutManager(recipeStepsLayoutManager);
        recipeStepsRecycleView.setHasFixedSize(true);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Bundle args = getActivity().getIntent().getBundleExtra("BUNDLE");
        recipeID = args.getInt("Recipe_ID");
        recipeTitle = args.getString("Recipe Title");
        arrayListObject = (ArrayList<IngredientsList>) args.getSerializable("INGREDIENT_ARRAY_LIST");
        arrayRecipeListObject = (ArrayList<RecipeStepsList>) args.getSerializable("DESCRIBE_ARRAY_LIST");
        Log.d(TAG, "Ingredient List info check1 " + arrayListObject);
        actionBar.setTitle(recipeTitle);
        getRecipeStepInformation(arrayListObject, arrayRecipeListObject, recipeTitle);
        return rootView;
    }


    /*-------------------creating Add Widget Option Menu----------------------------------*/
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        Cursor cursor;
        Uri uri_checkValue;
        switch (item.getItemId()) {
            case R.id.add_recipe_widget:
                //Toast.makeText(getContext(), "Add Widget Clicked", Toast.LENGTH_SHORT).show();
                //Checking recipe_ID already added or not to Database, if already added it will show already added message
                uri_checkValue = RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI;
                uri_checkValue = uri_checkValue.buildUpon().appendPath(String.valueOf(recipeID)).build();
                cursor = getActivity().getContentResolver().query(uri_checkValue,
                        null,
                        null,
                        null,
                        null);
                if ((cursor != null) && (cursor.getCount() > 0)) {
                    Toast.makeText(getContext(), recipeTitle + "Already Added to Widget", Toast.LENGTH_SHORT).show();
                } else {
                    //if new record, first delete all records in table and add new record
                    //only one recipe information required in database to setup a widget
                    //multiple recipes not required , all recipes delete before adding new recipe
                    uri_checkValue = RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI;
                    int deleteCheck = getActivity().getContentResolver().delete(uri_checkValue, null, null);
                    // COMPLETED (2) Delete a single row of data using a ContentResolver
                    if (deleteCheck >= 0) {
                        //After deleting all records in table and add new record to database
                        //only one record going to add to database
                        /*------------------------Insert Widget----------------------------*/
                        ContentValues contentValues = null;
                        for (int i = 0; i < arrayListObject.size(); i++) {
                            contentValues = new ContentValues();
                            contentValues.put(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_ID, recipeID);
                            contentValues.put(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_TITLE, recipeTitle);
                            contentValues.put(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_INGREDIENT, arrayListObject.get(i).getIngredient());
                            contentValues.put(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_QUANTITY, arrayListObject.get(i).getQuantity());
                            contentValues.put(RecipeContract.RecipeContractInfo.COLUMN_RECIPE_MEASURE, arrayListObject.get(i).getMeasure());
                            uri_checkValue = getActivity().getContentResolver().insert(RecipeContract.RecipeContractInfo.RECIPE_CONTENT_URI, contentValues);
                        }

                /*------------------------Insert Widget----------------------------*/
                        if (uri_checkValue != null) {
                            RecipeInfoProviderWidget.sendRefreshBroadcast(getContext());
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_PREF_RECIPE_TITLE, getContext().MODE_PRIVATE).edit();
                            editor.putString("recipe_title",recipeTitle);
                            editor.apply();
                            Toast.makeText(getContext(), recipeTitle + "Added to Widget", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    }

                }
                cursor.close();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    /*-------------------creating Add Widget Option Menu----------------------------------*/


    private void getRecipeStepInformation(ArrayList<IngredientsList> arrayListObjects, ArrayList<RecipeStepsList> arrayRecipeListObjects, String recipeTitleTransfer) {
        ArrayList<IngredientsList> ingredientsListInfo = arrayListObjects;
        ArrayList<RecipeStepsList> recipeStepsListInfo = arrayRecipeListObjects;
        String recipe_Titile = recipeTitleTransfer;
        Log.d(TAG, "Ingredient List info check2 " + ingredientsListInfo);
        recipeStepsAdapter = new RecipeStepsAdapter(ingredientsListInfo, recipeStepsListInfo, recipe_Titile, getContext());
        recipeStepsRecycleView.setAdapter(recipeStepsAdapter);
    }

    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(INGREDIENT_LIST, (ArrayList<IngredientsList>) arrayListObject);
        currentState.putSerializable(RECIPESTEPS_LIST, (ArrayList<RecipeStepsList>) arrayRecipeListObject);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }
}