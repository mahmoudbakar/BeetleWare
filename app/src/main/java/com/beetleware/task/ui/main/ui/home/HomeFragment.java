package com.beetleware.task.ui.main.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseFragment;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomeViewModel> {

    @BindView(R.id.txtSoldCount)
    TextView txtSoldCount;
    @BindView(R.id.txtProductsCount)
    TextView txtProductsCount;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        viewModel.getData();
    }

    @Override
    public HomeViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public void subscribeToLiveData() {
        super.subscribeToLiveData();
        viewModel.getProductsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txtProductsCount.setText(integer + "");
            }
        });
        viewModel.getSoldItemsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txtSoldCount.setText(integer + "");
            }
        });
    }
}