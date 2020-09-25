package com.beetleware.task.network;

import com.android.volley.Request;
import com.beetleware.task.models.responses.counters.CountersResponse;
import com.beetleware.task.models.responses.profile.ProfileResponse;
import com.beetleware.task.models.responses.signin.SigninResponse;
import com.beetleware.task.utils.MyPreference;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class API {

    public static String TAG_DEFUALT = "BAKAR API";
    private static API api = null;
    private static Gson gson;
    private MyPreference preference;
    private Requests requests;

    private API(Gson mGson, MyPreference preference) {
        this.preference = preference;
        gson = mGson;
        this.requests = Requests.getInstance(this.preference);
    }

    public static API getInstance(Gson mGson, MyPreference preference) {
        if (api == null) {
            api = new API(mGson, preference);
        }
        return api;
    }

    public void login(String userName, String password, OnResponse.Object<SigninResponse> responseListner, ErrorResponse<ErrorModel> fail) {
        final String TAG = "LOGIN";
        String url = "auth";
        Map<String, String> body = new HashMap<>();
        body.put("username", userName);
        body.put("password", password);
        requests.multiPartRequest(
                Request.Method.POST,
                url,
                body,
                null,
                new OnResponse.Object<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        SigninResponse signinResponse = gson.fromJson(object.toString(), SigninResponse.class);
                        preference.setToken(signinResponse.getData().getAuthorization());
                        responseListner.onSuccess(signinResponse);
                    }
                },
                fail,
                TAG);
    }

    public void getProfile(OnResponse.Object<ProfileResponse> responseListner, ErrorResponse<ErrorModel> fail) {
        final String TAG = "PROFILE";
        String url = "profile-show";
        requests.jsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Request.Priority.IMMEDIATE,
                new OnResponse.Object<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        ProfileResponse profileResponse = gson.fromJson(object.toString(), ProfileResponse.class);
                        responseListner.onSuccess(profileResponse);
                    }
                },
                fail,
                TAG);
    }

    public void getSoldItems(OnResponse.Object<CountersResponse> responseListner, ErrorResponse<ErrorModel> fail) {
        final String TAG = "SOLD_ITEMS";
        String url = "sold-items";
        requests.jsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Request.Priority.IMMEDIATE,
                new OnResponse.Object<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        CountersResponse countersResponse = gson.fromJson(object.toString(), CountersResponse.class);
                        responseListner.onSuccess(countersResponse);
                    }
                },
                fail,
                TAG);
    }

    public void getProductsCount(OnResponse.Object<CountersResponse> responseListner, ErrorResponse<ErrorModel> fail) {
        final String TAG = "PRODUCTS_COUNT";
        String url = "products";
        requests.jsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Request.Priority.IMMEDIATE,
                new OnResponse.Object<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        CountersResponse countersResponse = gson.fromJson(object.toString(), CountersResponse.class);
                        responseListner.onSuccess(countersResponse);
                    }
                },
                fail,
                TAG);
    }

}
