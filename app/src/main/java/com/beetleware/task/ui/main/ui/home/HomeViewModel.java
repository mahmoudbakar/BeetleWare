package com.beetleware.task.ui.main.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beetleware.task.models.responses.counters.CountersResponse;
import com.beetleware.task.network.OnResponse;
import com.beetleware.task.ui.base.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<Integer> productsCount = new MutableLiveData<>();
    private MutableLiveData<Integer> soldItemsCount = new MutableLiveData<>();

    public LiveData<Integer> getSoldItemsCount() {
        return soldItemsCount;
    }

    public LiveData<Integer> getProductsCount() {
        return productsCount;
    }

    public void getData() {
        getSoldItems();
    }

    public void getSoldItems() {
        setIsLoading(true);
        callAPI().getSoldItems(new OnResponse.Object<CountersResponse>() {
            @Override
            public void onSuccess(CountersResponse object) {
                soldItemsCount.setValue(object.getData());
                getProducts();
            }
        }, this);
    }

    public void getProducts() {
        callAPI().getProductsCount(new OnResponse.Object<CountersResponse>() {
            @Override
            public void onSuccess(CountersResponse object) {
                productsCount.setValue(object.getData());
                setIsLoading(false);
            }
        }, this);
    }
}