package com.beetleware.task.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleRequestQueue {
    private static SingleRequestQueue instance;
    private RequestQueue requestQueue;

    private SingleRequestQueue(Context context) {
        requestQueue = getRequestQueue(context);
    }

    static synchronized SingleRequestQueue getInstance(Context mContext) {
        if (instance == null) {
            instance = new SingleRequestQueue(mContext);
        }
        return instance;
    }

    RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
