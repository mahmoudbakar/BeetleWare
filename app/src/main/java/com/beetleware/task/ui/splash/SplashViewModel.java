package com.beetleware.task.ui.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beetleware.task.models.responses.profile.ProfileResponse;
import com.beetleware.task.network.OnResponse;
import com.beetleware.task.ui.base.BaseViewModel;

public class SplashViewModel extends BaseViewModel {

    MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>();

    public LiveData<Boolean> getIsAuthorized() {
        return isAuthorized;
    }

    public void getProfile() {
        callAPI().getProfile(new OnResponse.Object<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse object) {
                isAuthorized.setValue(object.getData().isIsActive());
                setIsLoading(false);
            }
        }, this);
    }
}
