package com.ethanco.mysmallsample.app.main;

import com.ethanco.mysmallsample.app.main.model.TimeModel;
import com.ethanco.mysmallsample.app.main.model.abs.ITimeModel;
import com.ethanco.mysmallsample.app.main.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by EthanCo on 2016/8/29.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TimeModelTest {
    MainActivity activity = Robolectric.setupActivity(MainActivity.class);
    ITimeModel timeModel = new TimeModel();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTime() {

    }

    @Test
    public void saveTime() {
        timeModel.saveTime("20150105");
    }
}
