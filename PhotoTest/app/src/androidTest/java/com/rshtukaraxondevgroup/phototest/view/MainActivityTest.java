package com.rshtukaraxondevgroup.phototest.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import com.rshtukaraxondevgroup.phototest.R;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.rshtukaraxondevgroup.phototest.view.ImageViewHasDrawableMatcher.hasDrawable;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubCameraIntent() {
        ActivityResult result = createImageCaptureActivityResultStub();

        // Stub the Intent.
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }

    @Test
    public void takePhoto() {
        // Check that the ImageView doesn't have a drawable applied.
        onView(ViewMatchers
                .withId(R.id.image_view))
                .check(matches(not(hasDrawable())));

        // Click on the button that will trigger the stubbed intent.
        onView(withId(R.id.button_take_photo))
                .perform(click());

        // With no user interaction, the ImageView will have a drawable.
//        onView(withId(R.id.image_view)).check(matches(hasDrawable()));
    }

    @Test
    public void clickOnEdit() {
        onView(withId(R.id.button_take_photo))
                .perform(click());
        onView(withId(R.id.button_edit))
                .perform(click());
        intended(hasComponent(EditActivity.class.getName()));
    }

    @Test
    public void clickOnUpload() {
        onView(withId(R.id.button_take_photo))
                .perform(click());
        onView(withId(R.id.button_upload))
                .perform(click());
        intended(hasComponent(UploadActivity.class.getName()));
    }

    private ActivityResult createImageCaptureActivityResultStub() {
        // Put the drawable in a bundle.
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", BitmapFactory.decodeResource(
                mIntentsRule.getActivity().getResources(), R.drawable.ic_launcher_background));

        // Create the Intent that will include the bundle.
        Intent resultData = new Intent();
        resultData.putExtras(bundle);

        // Create the ActivityResult with the Intent.
        return new ActivityResult(Activity.RESULT_OK, resultData);
    }
}