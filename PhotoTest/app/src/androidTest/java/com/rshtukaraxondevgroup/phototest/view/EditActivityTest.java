package com.rshtukaraxondevgroup.phototest.view;

import android.content.Intent;

import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.rshtukaraxondevgroup.phototest.view.ImageViewHasDrawableMatcher.hasDrawable;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditActivityTest {

    @Rule
    public ActivityTestRule<EditActivity> activityTestRule = new ActivityTestRule<>(EditActivity.class,
            true, false);

    @Before
    public void launchActivity() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_URI, "file:///storage/emulated/0/Pictures/PhotoDemo/IMG_20181101_174616.jpg");
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void takePhoto_drawableIsApplied() {
        onView(withId(R.id.button_save))
                .perform(click());
        onView(withId(R.id.image_view))
                .check(matches(hasDrawable()));
    }
}