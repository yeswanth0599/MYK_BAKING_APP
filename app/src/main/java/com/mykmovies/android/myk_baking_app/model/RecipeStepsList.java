package com.mykmovies.android.myk_baking_app.model;

import java.io.Serializable;

/**
 * Created by yeswa on 12-02-2018.
 */

public class RecipeStepsList implements Serializable {
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
