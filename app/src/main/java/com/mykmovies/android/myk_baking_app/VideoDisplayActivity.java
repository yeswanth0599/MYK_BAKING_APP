package com.mykmovies.android.myk_baking_app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mykmovies.android.myk_baking_app.fragment.VideoDisplayFragment;

public class VideoDisplayActivity extends AppCompatActivity {

        private boolean mTwoPane;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_display);
                if (savedInstanceState == null) {
                VideoDisplayFragment videoDisplayFragment = new VideoDisplayFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_display_fragment_container, videoDisplayFragment)
                        .addToBackStack(null)
                        .commit();

        }

    }

}
