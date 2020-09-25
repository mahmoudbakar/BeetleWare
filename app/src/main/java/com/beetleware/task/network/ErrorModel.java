package com.beetleware.task.network;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ErrorModel {

    private boolean show;

    @SerializedName("message")
    private String error;

    private int responseCode;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Nullable
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}