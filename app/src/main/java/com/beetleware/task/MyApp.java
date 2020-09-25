package com.beetleware.task;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.beetleware.task.network.Requests;
import com.beetleware.task.utils.LocaleHelper;

public class MyApp extends Application {
    private static Application application;

    public static Context getContext() {
        return application.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(@NonNull Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Requests.getInstance(this);
    }
}