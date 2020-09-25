package com.beetleware.task.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beetleware.task.BuildConfig;
import com.beetleware.task.utils.MyPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requests {

    private static final int TIMEOUT = 10000;
    private static final int RETRIES = 3;
    public static String TAG_DEFUALT = "defualt";
    private static Requests instance;
    private static MyPreference preference;
    String TAG = "BAKAR";
    private SingleRequestQueue singleRequestQueue;
    private Gson gson;

    private Requests(Context context) {
        singleRequestQueue = SingleRequestQueue.getInstance(context);
        gson = new GsonBuilder().create();
    }

    public static Requests getInstance(Context context) {
        if (instance == null) {
            instance = new Requests(context);
        }
        return instance;
    }

    public static Requests getInstance(MyPreference mPreference) {
        if (instance == null) {
            throw new IllegalStateException(Requests.class.getSimpleName() + "Is not initialized, Call getInstance(sendContextHere) first");
        }
        preference = mPreference;
        return instance;
    }

    public void jsonObjectRequest(int requestType,
                                  String url,
                                  String requestJsonObject,
                                  final Request.Priority priority,
                                  final OnResponse.Object<JSONObject> responseListener,
                                  ErrorResponse<ErrorModel> errorResponse, String apiTAG) {
        url = BuildConfig.BASE_URL + url;
        Log.wtf(TAG + "SEPARATE", "----------------------------------------------------------------------------------------------------------------");
        Log.wtf(TAG + "URL", url + "     " + requestType);
        if (requestJsonObject != null) {
            Log.wtf(TAG + "REQUEST", requestJsonObject);
        } else {
            Log.wtf(TAG + "REQUEST", "There is no RequestObject for this");
        }
        JSONObject jsonObject = null;
        if (requestJsonObject != null) {
            try {
                jsonObject = new JSONObject(requestJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JsonObjectRequest request = new JsonObjectRequest(requestType, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.wtf(TAG + " RESPONSE " + apiTAG, response.toString());
                        responseListener.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error, errorResponse, apiTAG);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Priority getPriority() {
                return priority;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = getMyHeaders();
                header.put("Content-Type", "application/json");
                return header;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TAG);
        request.setShouldCache(false);
        singleRequestQueue.addToRequestQueue(request);
    }

    public void multiPartRequest(int method, String url,
                                 Map<String, String> text,
                                 Map<String, FileModel> files,
                                 final OnResponse.Object<JSONObject> responseListener,
                                 ErrorResponse<ErrorModel> errorResponse, String apiTAG) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(method, BuildConfig.BASE_URL + url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    Log.wtf(TAG + apiTAG, result.toString());
                    responseListener.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error, errorResponse, apiTAG);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                for (Map.Entry entry :
                        text.entrySet()) {
                    parameters.put(entry.getKey() + "", entry.getValue() + "");
                }
                return parameters;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                if (files != null) {
                    for (Map.Entry entry :
                            files.entrySet()) {
                        FileModel fileModel = ((FileModel) entry.getValue());
                        params.put(entry.getKey().toString(), new DataPart(fileModel.getName(), fileModel.getFile(), fileModel.getMime()));
                    }
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getMyHeaders();
            }
        };

        multipartRequest.setShouldCache(false);
        singleRequestQueue.addToRequestQueue(multipartRequest);
    }

    private Map<String, String> getMyHeaders() {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Language", preference.getLanguage() + "");
        headers.put("Authorization", "Bearer " + preference.getToken());
        for (Map.Entry entry :
                headers.entrySet()) {
            Log.wtf(TAG + " HEADERS", entry.getKey() + " " + entry.getValue());
        }
        return headers;
    }

    private void handleError(VolleyError error, ErrorResponse<ErrorModel> errorResponse, String apiTAG) {
        int responseCode = 600;
        if (error.networkResponse != null) {
            responseCode = error.networkResponse.statusCode;
            ErrorModel errorModel = null;
            try {
                errorModel = gson.fromJson(new String(error.networkResponse.data), ErrorModel.class);
                Log.wtf(TAG + "ERROR", responseCode + new String(error.networkResponse.data));
            } catch (Exception ignored) {
            }
            if (errorModel == null) errorModel = new ErrorModel();
            errorModel.setResponseCode(responseCode);
            switch (responseCode) {
                case 400:
                    errorResponse.onBadRequest400(apiTAG, errorModel);
                    break;
                case 401:
                    errorResponse.onNotAuthorized401(apiTAG, errorModel);
                    break;
                case 403:
                    errorResponse.onForbidden403(apiTAG, errorModel);
                    break;
                case 404:
                case 406:
                    errorResponse.onNotFound404(apiTAG, errorModel);
                    break;
                case 422:
                    errorResponse.onMissingOrDuplicated409(apiTAG, errorModel);
                    break;
                case 429:
                    errorResponse.onTooManyRequests429(apiTAG, errorModel);
                    break;
                case 444:
                    errorResponse.onBusinessError444(apiTAG, errorModel);
                    break;
                case 500:
                    errorResponse.onServerSideError500(apiTAG, errorModel);
                    break;
                case 503:
                    errorResponse.onMaintenance503(apiTAG, errorModel);
                    break;
            }
        } else {
            errorResponse.onNoInternet600(apiTAG);
            Log.wtf(TAG + "ERROR", responseCode + " No Internet Connection Available");
        }
    }

}
