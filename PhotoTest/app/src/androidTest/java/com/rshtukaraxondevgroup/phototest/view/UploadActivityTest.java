package com.rshtukaraxondevgroup.phototest.view;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.rshtukaraxondevgroup.phototest.view.ImageViewHasDrawableMatcher.hasDrawable;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UploadActivityTest {

    @Rule
    public ActivityTestRule<UploadActivity> activityTestRule = new ActivityTestRule<>(UploadActivity.class,
            true, false);

//    @Rule
//    public IntentsTestRule<UploadActivity> mIntentsRule = new IntentsTestRule<>(UploadActivity.class,
//            true, false);

    @Before
    public void launchActivity() {

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_URI, "file:///storage/emulated/0/Pictures/PhotoDemo/sign-info-icon.png");
        activityTestRule.launchActivity(intent);

//        mIntentsRule.launchActivity(intent);
//        Instrumentation.ActivityResult result = createActivityResultStub();
//        intending(toPackage("com.android.contacts")).respondWith(result);
    }

    @Test
    public void sendPhotoDropBox() {
        onView(withId(R.id.button_dropbox))
                .perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.image_view))
                .check(matches(hasDrawable()));
    }

    @Test
    public void sendPhotoFirebase() {
        onView(withId(R.id.button_firebase))
                .perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            fail("timeout");
        }
        onView(withId(R.id.image_view))
                .check(matches(hasDrawable()));
    }

//    @Test
//    public void sendPhotoGoogleDrive() {
//        onView(withId(R.id.button_google_drive))
//                .perform(click());
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            fail("timeout");
//        }
//        onView(withId(R.id.image_view))
//                .check(matches(hasDrawable()));
//    }

//    private Instrumentation.ActivityResult createActivityResultStub() {
//        // Put the drawable in a bundle.
//        Bundle bundle = new Bundle();
//        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, "shtukarrv@gmail.com");
//
//        // Create the Intent that will include the bundle.
//        Intent resultData = new Intent();
//        resultData.putExtra(Constants.EXTRA_URI, "file:///storage/emulated/0/Pictures/PhotoDemo/sign-info-icon.png");
//        resultData.putExtras(bundle);
//
//        // Create the ActivityResult with the Intent.
//        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//    }
}