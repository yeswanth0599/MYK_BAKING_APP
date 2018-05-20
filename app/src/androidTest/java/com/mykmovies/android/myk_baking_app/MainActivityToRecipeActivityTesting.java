package com.mykmovies.android.myk_baking_app;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by yeswa on 18-03-2018.
 */

public class MainActivityToRecipeActivityTesting {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void mainScreenRecycleViewIteamCheck() {
        closeSoftKeyboard();
        onView(withId(R.id.baking_main_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recipe_steps_info_short_description)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps_info_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
    }
}
