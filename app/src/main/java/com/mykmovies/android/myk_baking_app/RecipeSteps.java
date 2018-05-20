package com.mykmovies.android.myk_baking_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mykmovies.android.myk_baking_app.adapter.RecipeStepsAdapter;
import com.mykmovies.android.myk_baking_app.fragment.RecipeStepsFragment;
import com.mykmovies.android.myk_baking_app.fragment.VideoDisplayFragment;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeSteps extends AppCompatActivity implements RecipeStepsAdapter.RecipeStepClickListener{

    private boolean mTwoPane;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
            if (savedInstanceState == null) {
                RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
                fragmentManager= getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_steps_fragment_container, recipeStepsFragment)
                        .addToBackStack("RecipeStepsBackStack")
                        .commit();
                if(findViewById(R.id.linearlayout_tab_view)!=null)
                {
                    mTwoPane=true;
                }
                else
                {
                    mTwoPane=false;
                }
            }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onRecipeStepItemClick(Bundle recipeStepsArgs,Context context) {
                //getBundleExtra("BUNDLE_RECIPE_STEP_DESCRIBE");
        ArrayList<RecipeStepsList> arrayRecipeListObjTransfer = (ArrayList<RecipeStepsList>) recipeStepsArgs.getSerializable("RECIPE_LIST");
        String recipeTitleTransfer=recipeStepsArgs.getString("recipe_title_transfer");
        String recipeStepDescriptionIntent = recipeStepsArgs.getString("recipe_description");
        String recipeStepVideoUrlIntent = recipeStepsArgs.getString("recipe_video_url");
        Integer itemPresentPostion = recipeStepsArgs.getInt("item_present_position");
        Integer itemMaxPosition = recipeStepsArgs.getInt("item_max_position");

/*******************************************************************************/
        Bundle argsTrasfterDataTabView=new Bundle();
        argsTrasfterDataTabView.putSerializable("RECIPE_LIST", (Serializable) arrayRecipeListObjTransfer);
        argsTrasfterDataTabView.putString("recipe_title_transfer",recipeTitleTransfer);
        argsTrasfterDataTabView.putInt("item_present_position", itemPresentPostion);
        argsTrasfterDataTabView.putInt("item_max_position", itemMaxPosition);
        argsTrasfterDataTabView.putString("recipe_description", recipeStepDescriptionIntent);
        argsTrasfterDataTabView.putString("recipe_video_url", recipeStepVideoUrlIntent);
        if(mTwoPane)
        {
            VideoDisplayFragment videoDisplayFragment=new VideoDisplayFragment();
            videoDisplayFragment.setArguments(argsTrasfterDataTabView);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_display_fragment_container, videoDisplayFragment)
                    .addToBackStack("RecipeStepsBackStack2")
                    .commit();
        }
        else
        {
            Intent intent = new Intent(context, VideoDisplayActivity.class);
            intent.putExtra("BUNDLE_RECIPE_STEP_DESCRIBE", argsTrasfterDataTabView);
            context.startActivity(intent);
        }

    }
}
