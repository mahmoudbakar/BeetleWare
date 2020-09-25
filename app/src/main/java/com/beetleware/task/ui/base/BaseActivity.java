package com.beetleware.task.ui.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.beetleware.task.R;
import com.beetleware.task.network.ErrorModel;
import com.beetleware.task.ui.splash.SplashActivity;
import com.beetleware.task.utils.LocaleHelper;
import com.beetleware.task.utils.MyPreference;
import com.beetleware.task.utils.NetworkUtils;
import com.beetleware.task.utils.UiHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    private static BaseActivity instance;
    public V viewModel;
    public MyPreference preference;
    public Gson gson;
    public UiHelper uiHelper;
    Unbinder unbinder;

    public static BaseActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preInit();
        viewModel = getViewModel();
        viewModel.setPreference(preference);
        viewModel.setGson(gson);
        subscribeToLiveData();
        setContentView(getLayout());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        unbinder = ButterKnife.bind(this);
        initActivity(savedInstanceState);
    }

    public abstract int getLayout();

    public abstract void initActivity(Bundle savedInstanceState);

    public void preInit() {
        instance = this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        if (preference == null) {
            preference = new MyPreference(gson);
        }
        LocaleHelper.setLocale(this, preference.getLanguage());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        uiHelper = new UiHelper(this);
    }

    public abstract V getViewModel();

    public void subscribeToLiveData() {
        viewModel.isCleanRestartRequired().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    preference.setToken("");
                    uiHelper.openActivity(SplashActivity.class, null, true);
                    finishAffinity();
                }
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    uiHelper.showProgressDialog(getString(R.string.message_loading));
                } else {
                    uiHelper.hideProgressDialog();
                }
            }
        });
        viewModel.getNetworkError().observe(this, new Observer<ErrorModel>() {
            @Override
            public void onChanged(ErrorModel errorModel) {
                if (errorModel.isShow()) {
                    uiHelper.showDialog(errorModel.getResponseCode() + "", errorModel.getError());
                }
            }
        });
        viewModel.getIsRestartRequired().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //uiHelper.openActivity(SplashActivity.class, null, true);
                    finishAffinity();
                }
            }
        });
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void invertStatusBarColors() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void hideToolbar() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void showBackArrow() {
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException ignored) {
        }
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.wtf("BAKAR", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("BAKAR", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("BAKAR", "printHashKey()", e);
        }
    }

}
