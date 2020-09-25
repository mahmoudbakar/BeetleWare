package com.beetleware.task.ui.main.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beetleware.task.ui.base.BaseViewModel;

public class SearchViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is empty for now");
    }

    public LiveData<String> getText() {
        return mText;
    }
}