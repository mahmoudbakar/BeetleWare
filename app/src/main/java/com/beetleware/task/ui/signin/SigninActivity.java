package com.beetleware.task.ui.signin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseActivity;
import com.beetleware.task.ui.main.MainBottomActivity;
import com.beetleware.task.utils.Regex;

import butterknife.BindView;
import butterknife.OnClick;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SigninActivity extends BaseActivity<SigninViewModel> {

    @BindView(R.id.edUserName)
    EditText edUserName;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.btnLogin)
    RelativeLayout btnLogin;
    private AwesomeValidation mAwesomeValidation;

    @Override
    public int getLayout() {
        return R.layout.activity_signin;
    }

    @Override
    public void initActivity(Bundle savedInstanceState) {
        hideToolbar();
        initValidators();
    }

    @Override
    public SigninViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SigninViewModel.class);
    }

    private void initValidators() {
        mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(edUserName, Regex.REGEX_NAME, getString(R.string.error_user_name));
        mAwesomeValidation.addValidation(edPassword, Regex.REGEX_PASSWORD, getString(R.string.error_password));
    }

    @Override
    public void subscribeToLiveData() {
        super.subscribeToLiveData();
        viewModel.getIsCredentialsInvalid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                uiHelper.showDialog(getString(R.string.label_invalid_credentials), getString(R.string.message_invalid_login));
            }
        });
        viewModel.getIsAuthorized().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    uiHelper.openActivity(MainBottomActivity.class, null, true);
                } else {
                    uiHelper.showDialog(getString(R.string.label_sorry), getString(R.string.message_inactive_account));
                }
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (mAwesomeValidation.validate()) {
            viewModel.login(edUserName.getText().toString(), edPassword.getText().toString());
        }
    }
}