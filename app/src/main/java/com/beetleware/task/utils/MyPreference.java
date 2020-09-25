package com.beetleware.task.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.beetleware.task.MyApp;
import com.google.gson.Gson;

public class MyPreference {
    private Gson gson;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public MyPreference(Gson gson) {
        preferences = MyApp.getContext().getSharedPreferences("TASK", Context.MODE_PRIVATE);
        this.gson = gson;
        editor = preferences.edit();
        editor.apply();
    }

    @Nullable
    public String getLanguage() {
        return preferences.getString("language", "en");
    }

    public void setLanguage(String language) {
        editor.putString("language", language);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

}
