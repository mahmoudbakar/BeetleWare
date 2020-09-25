package com.beetleware.task.ui.base;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beetleware.task.network.API;
import com.beetleware.task.network.ErrorModel;
import com.beetleware.task.network.ErrorResponse;
import com.beetleware.task.utils.MyPreference;
import com.google.gson.Gson;

public class BaseViewModel extends ViewModel implements ErrorResponse<ErrorModel> {

    public Gson gson;
    public MyPreference preference;
    private API api;

    //this to clean everything & restart the app
    private MutableLiveData<Boolean> cleanRestartRequired = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRestartRequired = new MutableLiveData<>();
    private MutableLiveData<ErrorModel> networkError = new MutableLiveData<>();

    public BaseViewModel() {
        cleanRestartRequired.setValue(false);
    }

    public API callAPI() {
        if (api == null) {
            api = API.getInstance(gson, preference);
        }
        return api;
    }

    public LiveData<Boolean> isCleanRestartRequired() {
        return cleanRestartRequired;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setPreference(MyPreference preference) {
        this.preference = preference;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getIsRestartRequired() {
        return isRestartRequired;
    }

    public LiveData<ErrorModel> getNetworkError() {
        return networkError;
    }

    @Override
    public void popError(ErrorModel errorModel) {
        errorModel.setShow(true);
        networkError.setValue(errorModel);
    }

    @Override
    public void onBadRequest400(String tag, ErrorModel errorModel) {
        setIsLoading(false);
        if (tag.equals("PROFILE")) {
            cleanRestartRequired.setValue(true);
        }
    }

    @Override
    public void onNotAuthorized401(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onPaymentRequired402(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onForbidden403(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onNotFound404(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onMethodNotAllowed405(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onRequestTimeOut408(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onMissingOrDuplicated409(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onUnsupportedMediaType415(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onUpdateRequired426(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onNoResponse427(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onTooManyRequests429(String tag, ErrorModel errorModel) {
        setIsLoading(false);
        popError(errorModel);
    }

    @Override
    public void onBusinessError444(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onServerSideError500(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onMaintenance503(String tag, ErrorModel errorModel) {
        setIsLoading(false);
    }

    @Override
    public void onNoInternet600(String tag) {
        setIsLoading(false);
    }
}
