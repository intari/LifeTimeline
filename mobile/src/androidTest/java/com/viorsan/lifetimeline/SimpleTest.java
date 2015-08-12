package com.viorsan.lifetimeline;

import android.util.Log;

import android.support.test.runner.AndroidJUnit4;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleTest {

    public static final String TAG = SimpleTest.class.getName();

    @BeforeClass
    static public void doThisFirstOnlyOnce() {
        // do initialization here, run once for all SillyTest tests
    }

    @Before
    public void setUp() {
        Log.d(TAG, "Setting up test");
    }
    @Test
    public void testPass() {
        Assert.assertEquals(2 + 2, 4);
    }
    @Test
    public void testVeryComplex() {
        Assert.assertTrue(Boolean.TRUE);
    }

    @After
    public void doThisLast() {
        // do termination here, run on every test method
    }
    @AfterClass
    static public void doThisLastOnlyOnce() {
        // do termination here, run once for all SillyTest tests
    }

}

