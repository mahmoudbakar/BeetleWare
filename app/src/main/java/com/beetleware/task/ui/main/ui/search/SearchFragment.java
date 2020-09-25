package com.beetleware.task.ui.main.ui.search;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseFragment;


public class SearchFragment extends BaseFragment<SearchViewModel> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public SearchViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SearchViewModel.class);
    }
}