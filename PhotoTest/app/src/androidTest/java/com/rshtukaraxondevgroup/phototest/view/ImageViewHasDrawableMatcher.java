package com.rshtukaraxondevgroup.phototest.view;

import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;

import androidx.test.espresso.matcher.BoundedMatcher;

/**
 * A Matcher for Espresso that checks if an ImageView has a drawable applied to it.
 */
public class ImageViewHasDrawableMatcher {

    public static BoundedMatcher<View, ImageView> hasDrawable() {
        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has drawable");
            }

            @Override
            public boolean matchesSafely(ImageView imageView) {
                return imageView.getDrawable() != null;
            }
        };
    }
}
