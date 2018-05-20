package com.mykmovies.android.myk_baking_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.model.IngredientsList;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeswa on 10-02-2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_INGREDIENTS = 0;
    private final int VIEW_TYPE_RECIPE_STEPS = 1;
    private final String TAG = "RecipeStepsAdapter";

    private List<IngredientsList> recipeIngredientList = new ArrayList<>();
    private List<RecipeStepsList> recipeStepsList = new ArrayList<>();
    private Context mContext;
    private String recipeTitle;
    private Bitmap bitmap;
    private RecipeStepClickListener recipeStepClickListener;
    public interface RecipeStepClickListener {
        void onRecipeStepItemClick(Bundle recipeStepsArgs,Context context);
    }

    public RecipeStepsAdapter(List<IngredientsList> recipeIngredientList, List<RecipeStepsList> recipeStepsList, String recipeTitle,Context context) {
        this.recipeIngredientList = recipeIngredientList;
        this.recipeStepsList = recipeStepsList;
        this.recipeTitle=recipeTitle;
        this.mContext = context;
        recipeStepClickListener = (RecipeStepClickListener) context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_INGREDIENTS) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_steps_info_list, parent, false);
            return new IngredientViewHolder(itemView);
        }

        if (viewType == VIEW_TYPE_RECIPE_STEPS) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.describe_recipe_steps_info, parent, false);
            return new RecipeStepsViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof IngredientViewHolder) {
            ((IngredientViewHolder) viewHolder).populate(recipeIngredientList.get(position));
        }

        if (viewHolder instanceof RecipeStepsViewHolder) {
            try {
                ((RecipeStepsViewHolder) viewHolder).populate(recipeStepsList.get(position - recipeIngredientList.size()));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {

        return recipeIngredientList.size() + recipeStepsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < recipeIngredientList.size()) {
            return VIEW_TYPE_INGREDIENTS;
        }

        if (position - recipeIngredientList.size() < recipeStepsList.size()) {
            return VIEW_TYPE_RECIPE_STEPS;
        }

        return -1;
    }

    /**
     * creating MainRecipeViewHolder Class
     */

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView quantityTextView;
        TextView measureTextView;
        TextView ingredientTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            quantityTextView = (TextView) itemView.findViewById(R.id.recipe_steps_info_quantity);
            measureTextView = (TextView) itemView.findViewById(R.id.recipe_steps_info_measure);
            ingredientTextView = (TextView) itemView.findViewById(R.id.recipe_steps_info_ingredient);
        }

        public void populate(IngredientsList ingredientsList) {
            quantityTextView.setText(String.valueOf(ingredientsList.getQuantity()));
            measureTextView.setText(ingredientsList.getMeasure());
            ingredientTextView.setText(ingredientsList.getIngredient());
        }
    }


    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //boolean mTwoPane;
        TextView shortDesciptionTextView;
        ImageView ShortDescriptionVideoThumbnailImageView;
        GlideDrawableImageViewTarget imagePreview;


        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            shortDesciptionTextView = (TextView) itemView.findViewById(R.id.recipe_steps_info_short_description);
            ShortDescriptionVideoThumbnailImageView = (ImageView) itemView.findViewById(R.id.recipe_steps_info_video_thumbnail);
            itemView.setOnClickListener(this);
        }

        public void populate(RecipeStepsList recipeStepsList) throws Throwable {
            shortDesciptionTextView.setText(recipeStepsList.getShortDescription());
            //ShortDescriptionVideoThumbnailImageView.setImageResource(R.mipmap.ic_image_video_thumbnail);
            //Bitmap imageThumbnail = createThumbnailFromPath(recipeStepsList.getVideoURL(), MediaStore.Images.Thumbnails.MINI_KIND);
            //ShortDescriptionVideoThumbnailImageView.setImageBitmap(imageThumbnail);



             Glide.with(mContext)
             .load(recipeStepsList.getThumbnailURL())
             .centerCrop()
             .placeholder(R.mipmap.ic_image_video_thumbnail)
             .crossFade()
             .into(ShortDescriptionVideoThumbnailImageView);

            //Bitmap bmFrame = ThumbnailUtils.createVideoThumbnail(recipeStepsList.getVideoURL(), MediaStore.Images.Thumbnails.MICRO_KIND);

            //MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            //mediaMetadataRetriever .setDataSource(recipeStepsList.getVideoURL(), new HashMap<String, String>());
            //Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(); //unit in microsecond
            //ShortDescriptionVideoThumbnailImageView.setImageResource();



        }

        @Override
        public void onClick(View view) {
            List<RecipeStepsList> stepsList = new ArrayList<>();
            int position = getAdapterPosition();
            String recipeTitleTransfer=recipeTitle;
            int recipeStepPresentPosition = position - recipeIngredientList.size();
            int recipeStepMaxPosition = recipeStepsList.size();
            RecipeStepsList recipeStepsDataTransfer = recipeStepsList.get(recipeStepPresentPosition);
            stepsList = recipeStepsList;
            //Intent intent = new Intent(mContext, VideoDisplayActivity.class);
            Bundle args = new Bundle();
            args.putString("recipe_title_transfer",recipeTitleTransfer);
            args.putSerializable("RECIPE_LIST", (Serializable) stepsList);
            args.putInt("item_present_position", recipeStepPresentPosition);
            args.putInt("item_max_position", recipeStepMaxPosition);
            args.putString("recipe_description", recipeStepsDataTransfer.getDescription());
            args.putString("recipe_video_url", recipeStepsDataTransfer.getVideoURL());
            Log.d(TAG, "Video URL Transferred " + recipeStepsDataTransfer.getVideoURL());
            Log.d(TAG, "recipeTitleTransferred Transferred " + recipeTitleTransfer);
            recipeStepClickListener.onRecipeStepItemClick(args,mContext);
                //intent.putExtra("BUNDLE_RECIPE_STEP_DESCRIBE", args);
                //mContext.startActivity(intent);
        }
    }
/*
    public Bitmap createThumbnailFromPath(String filePath, int type) {
        return ThumbnailUtils.createVideoThumbnail(filePath, type);
    }
    */
}

/***
 *
 * @param videoPath
 * @return
 * @throws Throwable


public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
Bitmap bitmap = null;
MediaMetadataRetriever mediaMetadataRetriever = null;
try {
mediaMetadataRetriever = new MediaMetadataRetriever();
if (Build.VERSION.SDK_INT >= 14)
mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
else
mediaMetadataRetriever.setDataSource(videoPath);
//   mediaMetadataRetriever.setDataSource(videoPath);
bitmap = mediaMetadataRetriever.getFrameAtTime();
} catch (Exception e) {
e.printStackTrace();
throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

} finally {
if (mediaMetadataRetriever != null) {
mediaMetadataRetriever.release();
}
}
return bitmap;
}
}   */
