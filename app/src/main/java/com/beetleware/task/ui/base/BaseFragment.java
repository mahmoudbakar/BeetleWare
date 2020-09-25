package com.beetleware.task.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.beetleware.task.network.ErrorModel;
import com.beetleware.task.ui.splash.SplashActivity;
import com.beetleware.task.utils.MyPreference;
import com.beetleware.task.utils.UiHelper;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    public V viewModel;
    public MyPreference preference;
    public Gson gson;
    public UiHelper uiHelper;
    public BaseActivity activity;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = BaseActivity.getInstance();
        uiHelper = activity.uiHelper;
        gson = activity.gson;
        preference = activity.preference;
        viewModel = getViewModel();
        viewModel.setPreference(preference);
        viewModel.setGson(gson);
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        //setTitle(getTitle());
        initView(view);
        subscribeToLiveData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract int getLayout();

    public abstract void initView(View view);

    public abstract V getViewModel();

    public void subscribeToLiveData() {
        viewModel.isCleanRestartRequired().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    preference.setToken("");
                    uiHelper.openActivity(SplashActivity.class, null, true);
                    activity.finishAffinity();
                }
            }
        });
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    uiHelper.showProgressDialog("Loading....");
                } else {
                    uiHelper.hideProgressDialog();
                }
            }
        });
        viewModel.getNetworkError().observe(getViewLifecycleOwner(), new Observer<ErrorModel>() {
            @Override
            public void onChanged(ErrorModel errorModel) {
                if (errorModel.isShow()) {
                    uiHelper.showDialog(errorModel.getResponseCode() + "", errorModel.getError());
                }
            }
        });
    }

}
