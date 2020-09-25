package com.beetleware.task.network;

public class LastRequest {

    private Object request;
    private OnResponse.Object response;
    private ErrorResponse<ErrorModel> errorResponse;
    private String TAG;

    public LastRequest(Object request, OnResponse.Object response, ErrorResponse<ErrorModel> errorResponse, String TAG) {
        this.request = request;
        this.response = response;
        this.errorResponse = errorResponse;
        this.TAG = TAG;
    }

    public Object getRequest() {
        return request;
    }

    public OnResponse.Object<Object> getResponse() {
        return response;
    }

    public ErrorResponse<ErrorModel> getErrorResponse() {
        return errorResponse;
    }

    public String getTAG() {
        return TAG;
    }
}
