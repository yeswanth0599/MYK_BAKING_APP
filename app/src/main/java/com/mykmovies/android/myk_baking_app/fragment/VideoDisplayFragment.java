package com.mykmovies.android.myk_baking_app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.mykmovies.android.myk_baking_app.R;
import com.mykmovies.android.myk_baking_app.VideoDisplayActivity;
import com.mykmovies.android.myk_baking_app.model.RecipeStepsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class VideoDisplayFragment extends Fragment {
    TextView RecipeDescription;
    SimpleExoPlayerView recipeDisplauSimpleExoPlayerView;
    private SimpleExoPlayer recipeDisplauSimpleExoPlayer;
    private String recipeStepDescriptionIntent;
    String recipeStepVideoUrlIntent;
    Button previousRecipeStep, nextRecipeStep;
    Integer itemPresentPostion, itemMaxPosition;
    RecipeStepsList recipeStepsList;
    ArrayList<RecipeStepsList> arrayRecipeListTotalObjects;
    FragmentManager fragmentManager;
    Toolbar mToolBar;
    ActionBar actionBar;
    private long exoPlayerPosition = 0;
    private final String TAG = "VideoDisplayFragment";
    public static final String RECIPE_STEP_DESCRIPTION = "recipe_step_description_savedState";
    public static final String RECIPE_VIDEO_PLAY_POSITION = "recipe_step_video_play_position";
    public static final String RECIPE_VIDEO_PLAY_STATUS = "recipe_step_video_play_status";
    private boolean playStatuscheck = true;
    private boolean mTwoPane;
    public String recipeTitle;
    Uri recipeStepVideoUrlIntent_Uri;
    //public static final String RECIPE_STEP_VIDEO_URL = "recipe_step_video_url_savedState";

    public VideoDisplayFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

    }

    /*public void setData(SimpleExoPlayer recipeDisplauSimpleExoPlayer) {
        this.recipeDisplauSimpleExoPlayer = recipeDisplauSimpleExoPlayer;
    }
    public SimpleExoPlayer getData() {
        return recipeDisplauSimpleExoPlayer;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity().findViewById(R.id.linearlayout_tab_view) != null) {
            mTwoPane = true;
            Bundle argsTransferredForTabView = this.getArguments();
            //.getBundleExtra("BUNDLE_RECIPE_STEP_DESCRIBE");
            recipeTitle = argsTransferredForTabView.getString("recipe_title_transfer");
            recipeStepDescriptionIntent = argsTransferredForTabView.getString("recipe_description");
            recipeStepVideoUrlIntent = argsTransferredForTabView.getString("recipe_video_url");
            itemPresentPostion = argsTransferredForTabView.getInt("item_present_position", 0);
            itemMaxPosition = argsTransferredForTabView.getInt("item_max_position", 0);
            arrayRecipeListTotalObjects = (ArrayList<RecipeStepsList>) argsTransferredForTabView.getSerializable("RECIPE_LIST");
        } else {
            Bundle args = getActivity().getIntent().getBundleExtra("BUNDLE_RECIPE_STEP_DESCRIBE");
            recipeTitle = args.getString("recipe_title_transfer");
            recipeStepDescriptionIntent = args.getString("recipe_description");
            recipeStepVideoUrlIntent = args.getString("recipe_video_url");
            itemPresentPostion = args.getInt("item_present_position");
            itemMaxPosition = args.getInt("item_max_position");
            arrayRecipeListTotalObjects = (ArrayList<RecipeStepsList>) args.getSerializable("RECIPE_LIST");

        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video_display, container, false);
        /*Toolbar setup for fragment*/

        /*Toolbar*/
        RecipeDescription = (TextView) rootView.findViewById(R.id.recipe_step_video_description);
        /*fetching data from the adapter through getIntent and apply in fragments*/
        recipeDisplauSimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.recipe_step_video_exoPlayer_view);
       /*----------------------------------Next Button onClick Listener Method-----------------*/

       /* Received Recipe Description apply to text view*/
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (mTwoPane) {
            mToolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
            mToolBar.setVisibility(View.INVISIBLE);
             /*creating ButtonViews*/
            previousRecipeStep = (Button) rootView.findViewById(R.id.button_previous_recipe_step);
            previousRecipeStep.setVisibility(View.GONE);
            nextRecipeStep = (Button) rootView.findViewById(R.id.button_next_recipe_step);
            nextRecipeStep.setVisibility(View.GONE);
       /*----------------------------------Previous Button onClick Listener Method-------------*/
        } else {
            /*---If single pane buttons will display---*/
            /*---Previous Step Button Implementation---*/
            previousRecipeStep = (Button) rootView.findViewById(R.id.button_previous_recipe_step);
            previousRecipe_Step(previousRecipeStep);
            /*----Next Step Button Implementation----*/
            nextRecipeStep = (Button) rootView.findViewById(R.id.button_next_recipe_step);
            nextRecipe_Step(nextRecipeStep);
            /*---Action bar hiding and creating new toolbar to implement stack----*/
            actionBar.hide();
            mToolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
            Log.d(TAG, "Received title Check " + recipeTitle);
            mToolBar.setTitle(recipeTitle);
            mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        }
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getContext(),"Fragment Back Pressed"+fragmentManager.getBackStackEntryCount(),Toast.LENGTH_SHORT).show();
                //getActivity().finish();
                //getFragmentManager().popBackStack("RecipeStepsBackStack", 0);
                onBackPressed();
            }
        });
        fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            playStatuscheck=savedInstanceState.getBoolean(RECIPE_VIDEO_PLAY_STATUS);
            recipeStepDescriptionIntent = savedInstanceState.getString(RECIPE_STEP_DESCRIPTION);
            exoPlayerPosition = savedInstanceState.getLong(RECIPE_VIDEO_PLAY_POSITION);

            //recipeStepVideoUrlIntent_Uri = Uri.parse(savedInstanceState.getString(RECIPE_STEP_VIDEO_URL));
        }
        Log.d(TAG, "Received URI Value Check " + recipeStepVideoUrlIntent);
        if (recipeStepVideoUrlIntent != null && !recipeStepVideoUrlIntent.isEmpty()) {
        /* Received Url playing through Exo Player*/

            recipeStepVideoUrlIntent_Uri = Uri.parse(recipeStepVideoUrlIntent);
            exoPlayerDisplay(recipeStepVideoUrlIntent_Uri);
            RecipeDescription.setText(recipeStepDescriptionIntent);


        } else {
            recipeDisplauSimpleExoPlayerView.setVisibility(View.GONE);
            RecipeDescription.setText(recipeStepDescriptionIntent);

        }
        return rootView;
    }

    public void onBackPressed() {
        //getFragmentManager().popBackStack("RecipeStepsBackStack", 0);
        super.getActivity().finish();

    }

    public void exoPlayerDisplay(Uri uri) {
        if (recipeDisplauSimpleExoPlayer == null) {

            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                recipeDisplauSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
                recipeDisplauSimpleExoPlayerView.setPlayer(recipeDisplauSimpleExoPlayer);
                recipeDisplauSimpleExoPlayer.prepare(mediaSource);
                recipeDisplauSimpleExoPlayer.setPlayWhenReady(playStatuscheck);
                if (exoPlayerPosition > 0) {
                    recipeDisplauSimpleExoPlayer.seekTo(exoPlayerPosition);
                    recipeDisplauSimpleExoPlayer.setPlayWhenReady(playStatuscheck);
                }

            } catch (Exception e) {
                Log.e(TAG, "exoplayer_error" + e.toString());
            }
        }
    }

    public void previousRecipe_Step(View v) {
        if (itemPresentPostion == 0) {
            previousRecipeStep.setEnabled(false);
        } else {
            previousRecipeStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                    itemPresentPostion--;
                    if (recipeDisplauSimpleExoPlayer != null) {
                        releasePlayer();
                    }
                    transferData(itemPresentPostion);
                }
            });
        }
    }

    public void nextRecipe_Step(View v) {
        if (itemMaxPosition.equals(itemPresentPostion + 1)) {
            nextRecipeStep.setEnabled(false);
        } else {
            nextRecipeStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                    itemPresentPostion++;
                    if (recipeDisplauSimpleExoPlayer != null) {
                        releasePlayer();
                    }
                    transferData(itemPresentPostion);
                    //Toast.makeText(getContext(),"total number of objects"+arrayRecipeListTotalObjects.size(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void transferData(int position) {
        recipeStepsList = arrayRecipeListTotalObjects.get(position);
        List<RecipeStepsList> stepsList_check = new ArrayList<>();
        stepsList_check = arrayRecipeListTotalObjects;
        Intent intent = new Intent(getContext(), VideoDisplayActivity.class);
        Bundle argsNextPrevDataTransfter = new Bundle();
        argsNextPrevDataTransfter.putString("recipe_title_transfer", recipeTitle);
        argsNextPrevDataTransfter.putSerializable("RECIPE_LIST", (Serializable) stepsList_check);
        argsNextPrevDataTransfter.putInt("item_present_position", itemPresentPostion);
        argsNextPrevDataTransfter.putInt("item_max_position", itemMaxPosition);
        argsNextPrevDataTransfter.putString("recipe_description", recipeStepsList.getDescription());
        argsNextPrevDataTransfter.putString("recipe_video_url", recipeStepsList.getVideoURL());
        intent.putExtra("BUNDLE_RECIPE_STEP_DESCRIBE", argsNextPrevDataTransfter);
        Log.d(TAG, "Video URL Transferred " + recipeStepsList.getVideoURL());
        getContext().startActivity(intent);
    }
    private void saveState() {
        if (recipeDisplauSimpleExoPlayer != null) {
            exoPlayerPosition = recipeDisplauSimpleExoPlayer.getCurrentPosition();
            playStatuscheck=recipeDisplauSimpleExoPlayer.getPlayWhenReady();
        }
    }
    public void onSaveInstanceState(Bundle currentState) {
        saveState();
        super.onSaveInstanceState(currentState);
        currentState.putBoolean(RECIPE_VIDEO_PLAY_STATUS,playStatuscheck);
        currentState.putLong(RECIPE_VIDEO_PLAY_POSITION, exoPlayerPosition);
        currentState.putString(RECIPE_STEP_DESCRIPTION, recipeStepDescriptionIntent);
        //currentState.putParcelable(RECIPE_STEP_VIDEO_URL, recipeStepVideoUrlIntent_Uri);

    }

    //public void onActivityCreated(Bundle savedInstanceState) {
        //super.onActivityCreated(savedInstanceState);
       // fragmentManager = getFragmentManager();

    //}

    private void releasePlayer() {
        saveState();
        recipeDisplauSimpleExoPlayer.stop();
        recipeDisplauSimpleExoPlayer.release();
        recipeDisplauSimpleExoPlayer = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (recipeDisplauSimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onPause() {
        super.onPause();
        if (recipeDisplauSimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onStop() {
        super.onStop();
        if (recipeDisplauSimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onResume() {
        super.onResume();
        if (recipeDisplauSimpleExoPlayer == null) {
            exoPlayerDisplay(recipeStepVideoUrlIntent_Uri);
        }

    }

}
