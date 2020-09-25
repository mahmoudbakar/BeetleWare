package com.beetleware.task.network;

import java.util.List;

public interface OnResponse {

    public interface Array<T> {
        public void onSuccess(List<T> list);
    }

    public interface Object<T> {
        public void onSuccess(T object);
    }
}
