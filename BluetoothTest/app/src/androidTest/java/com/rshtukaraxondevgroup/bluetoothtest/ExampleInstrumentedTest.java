package com.rshtukaraxondevgroup.bluetoothtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented profile, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under profile.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.rshtukaraxondevgroup.bluetoothtest", appContext.getPackageName());
    }
}
