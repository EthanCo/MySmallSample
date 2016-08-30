package com.ethanco.mysmallsample.app.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ethanco.mysmallsample.app.main.model.TimeModel;
import com.ethanco.mysmallsample.app.main.model.abs.ITimeModel;
import com.ethanco.mysmallsample.app.main.view.activity.MainActivity;
import com.ethanco.mysmallsample.lib.utils.config.AppConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by EthanCo on 2016/8/29.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRele = new ActivityTestRule<>(MainActivity.class);
    ITimeModel timeModel;

    @Before
    public void setUp() {
        timeModel = new TimeModel();
    }

    @Test
    public void getTime() {
        assertEquals(true, true);
    }

    @Test
    public void saveTime() {
        timeModel.saveTime("20150105");
        String time = AppConfig.getInstance().getTime();
        assertEquals(time, "20150105");
    }
}
