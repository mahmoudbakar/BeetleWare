package com.beetleware.task.ui.main.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beetleware.task.models.responses.profile.ProfileResponse;
import com.beetleware.task.network.OnResponse;
import com.beetleware.task.ui.base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel {

    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> image = new MutableLiveData<>();

    public LiveData<String> getFullName() {
        return fullName;
    }

    public LiveData<String> getImage() {
        return image;
    }

    public void getProfile() {
        setIsLoading(true);
        callAPI().getProfile(new OnResponse.Object<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse object) {
                fullName.setValue(object.getData().getName());
                image.setValue(object.getData().getAvatar());
                setIsLoading(false);
            }
        }, this);
    }
}