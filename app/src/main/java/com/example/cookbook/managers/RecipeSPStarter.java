package com.example.cookbook.managers;

import android.app.Application;

import com.example.cookbook.utils.RecipeSP;

public class RecipeSPStarter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RecipeSP.init(this);
    }
}
