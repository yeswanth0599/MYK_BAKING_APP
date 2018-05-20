package com.mykmovies.android.myk_baking_app;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by yeswa on 12-03-2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTesting {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void mainScreenRecycleViewIteamCheck() {
        closeSoftKeyboard();
        Intents.init();
        //onView(withId(R.id.recipe_steps_info_short_description)).check(matches(withText(TEA_NAME)));
        onView(withId(R.id.baking_main_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(hasComponent(RecipeSteps.class.getName()));;

    }

   // public void recipeDescriptionCheck() {

   // }
}


