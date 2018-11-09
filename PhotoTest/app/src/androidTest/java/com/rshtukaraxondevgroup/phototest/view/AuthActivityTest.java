package com.rshtukaraxondevgroup.phototest.view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.rshtukaraxondevgroup.phototest.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.StringRes;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AuthActivityTest {
    private String mEmail;
    private String mPassword;
    private String mSuccessMsg;
    private String mFailedMsg;

    @Rule
    public ActivityTestRule<AuthActivity> activityTestRule = new ActivityTestRule<>(AuthActivity.class);

    @Before
    public void setData() {
        mEmail = "codevscolor@gmail.com";
        mPassword = "password";
        mSuccessMsg = "Authorization successful";
        mFailedMsg = "Authorization failed";
    }

    @After
    public void launchActivity() {
        FirebaseAuth.getInstance().signOut();
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void emailIsEmpty() {
        onView(withId(R.id.et_email))
                .perform(clearText());
        onView(withId(R.id.btn_sign_in))
                .perform(click());
        onView(withId(R.id.et_email))
                .check(matches(withError(getString(R.string.required))));
    }

    @Test
    public void passwordIsEmpty() {
        onView(withId(R.id.et_email))
                .perform(typeText(mEmail), closeSoftKeyboard());
        onView(withId(R.id.et_password))
                .perform(clearText());
        onView(withId(R.id.btn_sign_in))
                .perform(click());
        onView(withId(R.id.et_password))
                .check(matches(withError(getString(R.string.required))));
    }

    @Test
    public void loginFailed() {
        onView(withId(R.id.et_email))
                .perform(typeText(mEmail), closeSoftKeyboard());
        onView(withId(R.id.et_password))
                .perform(typeText(mPassword), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in))
                .perform(click());
        onView(withText(mFailedMsg))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginSuccessfully_shouldShowToast() {
        onView(withId(R.id.et_email))
                .perform(typeText("test@test.ua"), closeSoftKeyboard());
        onView(withId(R.id.et_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in))
                .perform(click());
        onView(withText(mSuccessMsg))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    private String getString(@StringRes int resourceId) {
        return activityTestRule.getActivity().getString(resourceId);
    }

    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof EditText) {
                    return ((EditText) item).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message" + expected + ", find it!");
            }
        };
    }
}