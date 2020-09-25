package com.beetleware.task.ui.signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beetleware.task.models.responses.profile.ProfileResponse;
import com.beetleware.task.models.responses.signin.SigninResponse;
import com.beetleware.task.network.ErrorModel;
import com.beetleware.task.network.OnResponse;
import com.beetleware.task.ui.base.BaseViewModel;

public class SigninViewModel extends BaseViewModel {

    MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>();
    MutableLiveData<Boolean> isCredentialsInvalid = new MutableLiveData<>();

    public LiveData<Boolean> getIsCredentialsInvalid() {
        return isCredentialsInvalid;
    }

    public LiveData<Boolean> getIsAuthorized() {
        return isAuthorized;
    }

    public void login(String userName, String password) {
        setIsLoading(true);
        callAPI().login(userName, password, new OnResponse.Object<SigninResponse>() {
            @Override
            public void onSuccess(SigninResponse object) {
                getProfile();
            }
        }, this);
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

    @Override
    public void onNotAuthorized401(String tag, ErrorModel errorModel) {
        super.onNotAuthorized401(tag, errorModel);
        if (tag.equals("LOGIN")){
            isCredentialsInvalid.setValue(true);
        }
    }
}
