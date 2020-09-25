package com.beetleware.task.ui.splash;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseActivity;
import com.beetleware.task.ui.main.MainBottomActivity;
import com.beetleware.task.ui.signin.SigninActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initActivity(Bundle savedInstanceState) {
        hideToolbar();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (preference.getToken().equals("")) {
                    uiHelper.openActivity(SigninActivity.class, null, true);
                } else {
                    viewModel.getProfile();
                }
            }
        }, 3000);
    }

    @Override
    public SplashViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SplashViewModel.class);
    }

    @Override
    public void subscribeToLiveData() {
        super.subscribeToLiveData();
        viewModel.getIsAuthorized().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    uiHelper.openActivity(MainBottomActivity.class, null, true);
                } else {
                    uiHelper.openActivity(SigninActivity.class, null, true);
                }
            }
        });
    }
}