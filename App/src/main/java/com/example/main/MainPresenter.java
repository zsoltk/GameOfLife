package com.example.main;

import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class MainPresenter {

    @RootContext
    MainActivity view;

    void alert() {
        Toast.makeText(view, "Testing!", Toast.LENGTH_SHORT).show();
    }
}
