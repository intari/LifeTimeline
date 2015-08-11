package com.viorsan.lifetimeline;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.test.TouchUtils;
import junit.framework.Assert;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.08.15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends
        ActivityInstrumentationTestCase2<AppCompatActivity> {
    private ListView list=null;

    public MainActivityTest() {
        super(AppCompatActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        setActivityInitialTouchMode(false);

        AppCompatActivity activity=getActivity();

        list=(ListView)activity.findViewById(android.R.id.list);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void listCount() {
        Assert.assertEquals(25, list.getAdapter().getCount());
    }

    @Test
    public void keyEvents() {
        sendKeys("4*DPAD_DOWN");
        Assert.assertEquals(4, list.getSelectedItemPosition());
    }

    @Test
    public void touchEvents() {
        TouchUtils.scrollToBottom(this, getActivity(), list);
        getInstrumentation().waitForIdleSync();
        Assert.assertEquals(24, list.getLastVisiblePosition());
    }
}