package com.mykmovies.android.myk_baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.RecipeSteps;
import com.mykmovies.android.myk_baking_app.model.IngredientsList;
import com.mykmovies.android.myk_baking_app.model.MainRecipeList;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeswa on 10-02-2018.
 */

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.MainRecipeViewHolder> {
    private List<MainRecipeList> mainRecipeListValues=new ArrayList<>();
    private Context mContext;

    public MainRecipeAdapter(List<MainRecipeList> mainRecipeList,Context context)
    {
        this.mainRecipeListValues=mainRecipeList;
        this.mContext=context;

    }

    @Override
    public MainRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list,parent,false);
        return new MainRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecipeViewHolder holder, int position) {
                holder.recipeMainInfo.setText(mainRecipeListValues.get(position).getName());

                Glide.with(mContext)
                .load(mainRecipeListValues.get(position).getSteps().get(1).getThumbnailURL())
                .centerCrop()
                .placeholder(R.mipmap.ic_image_video_thumbnail)
                .crossFade()
                .into(holder.recipeMainThumbnail);

    }

    @Override
    public int getItemCount() {
        return mainRecipeListValues.size();
    }

    /**
     * creating MainRecipeViewHolder Class
     */
    public class MainRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeMainInfo;
        ImageView recipeMainThumbnail;
        public MainRecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            recipeMainInfo=(TextView)itemView.findViewById(R.id.baking_main_recipe_info);
            recipeMainThumbnail=(ImageView)itemView.findViewById(R.id.recipe_main_info_video_thumbnail);

        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            MainRecipeList mainRecipeList=mainRecipeListValues.get(position);
            ArrayList<IngredientsList> arrayListObject = mainRecipeList.getIngredients();
            ArrayList<RecipeStepsList> arrayRecipeListObject = mainRecipeList.getSteps();
            Intent intent=new Intent(mContext,RecipeSteps.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle args=new Bundle();
            args.putInt("Recipe_ID",mainRecipeList.getId());
            args.putString("Recipe Title",mainRecipeList.getName());
            args.putSerializable("INGREDIENT_ARRAY_LIST",(Serializable)arrayListObject);
            args.putSerializable("DESCRIBE_ARRAY_LIST",(Serializable)arrayRecipeListObject);
            intent.putExtra("BUNDLE",args);
            mContext.startActivity(intent);
        }
    }
}
