package com.google.lostfoundapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import org.litepal.LitePal;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize LitePal
        LitePal.initialize(this);
        //Initialize Baidu Map
        SDKInitializer.setAgreePrivacy(this, true);  // Set user agreement with the privacy policy
        SDKInitializer.initialize(this);  // Initialize the Baidu Map SDK
        //Use BD09LL coordinate
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
