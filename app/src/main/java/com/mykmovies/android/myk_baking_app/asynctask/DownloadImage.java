package com.mykmovies.android.myk_baking_app.asynctask;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;

import java.util.HashMap;

/**
 * Created by yeswa on 17-03-2018.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    String path;
    public DownloadImage( String path) {
        this.path=path;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap myBitmap = null;
        MediaMetadataRetriever mMRetriever = null;
        try {
            mMRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mMRetriever.setDataSource(path, new HashMap<String, String>());
            else
                mMRetriever.setDataSource(path);
            myBitmap = mMRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            if (mMRetriever != null) {
                mMRetriever.release();
            }
        }
        return myBitmap;
    }

}