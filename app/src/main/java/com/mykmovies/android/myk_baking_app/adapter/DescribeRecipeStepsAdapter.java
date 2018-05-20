package com.mykmovies.android.myk_baking_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeswa on 12-02-2018.
 */

public class DescribeRecipeStepsAdapter extends RecyclerView.Adapter<DescribeRecipeStepsAdapter.DescribeRecipeViewHolder> {

    private List<RecipeStepsList> recipeStepsList = new ArrayList<>();
    private Context mContext;

    public DescribeRecipeStepsAdapter(List<RecipeStepsList> recipeStepsList, Context context) {
        this.recipeStepsList = recipeStepsList;
        this.mContext = context;

    }

    @Override
    public DescribeRecipeStepsAdapter.DescribeRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.describe_recipe_steps_info, parent, false);
        return new DescribeRecipeStepsAdapter.DescribeRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DescribeRecipeStepsAdapter.DescribeRecipeViewHolder holder, int position) {
            holder.shortDesciptionTextView.setText(recipeStepsList.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return recipeStepsList.size();
    }

    /**
     * creating DescribeRecipeViewHolder Class
     */
    public class DescribeRecipeViewHolder extends RecyclerView.ViewHolder {
        TextView shortDesciptionTextView;

        public DescribeRecipeViewHolder(View itemView) {
            super(itemView);
            shortDesciptionTextView=(TextView)itemView.findViewById(R.id.recipe_steps_info_short_description);
        }
    }
}
