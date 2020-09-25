package com.beetleware.task.ui.main.ui.profile;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseFragment;
import com.beetleware.task.ui.splash.SplashActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment<ProfileViewModel> {

    @BindView(R.id.imageProfile)
    ImageView imageProfile;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtPosition)
    TextView txtPosition;
    @BindView(R.id.btnEdit)
    ImageButton btnEdit;
    @BindView(R.id.txtCustomers)
    TextView txtCustomers;
    @BindView(R.id.txtSales)
    TextView txtSales;
    @BindView(R.id.txtOffer)
    TextView txtOffer;
    @BindView(R.id.btnNotifications)
    CardView btnNotifications;
    @BindView(R.id.btnSettings)
    CardView btnSettings;
    @BindView(R.id.btnLogout)
    CardView btnLogout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initView(View view) {
        viewModel.getProfile();
    }

    @Override
    public ProfileViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public void subscribeToLiveData() {
        super.subscribeToLiveData();
        viewModel.getFullName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtUserName.setText(s);
            }
        });
        viewModel.getImage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(imageProfile).load(s).into(imageProfile);
            }
        });
    }

    @OnClick(R.id.btnNotifications)
    public void onBtnNotificationsClicked() {
        uiHelper.showToast(getString(R.string.message_nothing_to_do));
    }

    @OnClick(R.id.btnSettings)
    public void onBtnSettingsClicked() {
        if (preference.getLanguage().equals("ar")){
            preference.setLanguage("en");
        } else {
            preference.setLanguage("ar");
        }
        uiHelper.openActivity(SplashActivity.class, null, true);
    }

    @OnClick(R.id.btnLogout)
    public void onBtnLogoutClicked() {
        preference.setToken("");
        uiHelper.openActivity(SplashActivity.class, null, true);
    }

    @OnClick(R.id.btnEdit)
    public void onViewClicked() {
        uiHelper.showToast(getString(R.string.message_nothing_to_do));
    }
}