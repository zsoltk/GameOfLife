package com.example.main;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import com.example.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Bean
    MainPresenter presenter;

    @Click
    void testButton() {
        presenter.alert();
    }
}
