package com.beetleware.task.network;

public interface ErrorResponse<T> {
    public void popError(T errorModel);

    /**
     * Status Code 400
     */
    public void onBadRequest400(String tag, T errorModel);

    /**
     * Status Code 401
     */
    public void onNotAuthorized401(String tag, T errorModel);

    /**
     * Status Code 402
     */
    public void onPaymentRequired402(String tag, T errorModel);

    /**
     * Status Code 403
     */
    public void onForbidden403(String tag, T errorModel);

    /**
     * Status Code 404
     */
    public void onNotFound404(String tag, T errorModel);

    /**
     * Status Code 405
     */
    public void onMethodNotAllowed405(String tag, T errorModel);

    /**
     * Status Code 408
     */
    public void onRequestTimeOut408(String tag, T errorModel);

    /**
     * Status Code 409
     */
    public void onMissingOrDuplicated409(String tag, T errorModel);

    /**
     * Status Code 415
     */
    public void onUnsupportedMediaType415(String tag, T errorModel);

    /**
     * Status Code 426
     */
    public void onUpdateRequired426(String tag, T errorModel);

    /**
     * Status Code 427
     */
    public void onNoResponse427(String tag, T errorModel);

    /**
     * Status Code 429
     */
    public void onTooManyRequests429(String tag, T errorModel);

    /**
     * Status Code 444
     */
    public void onBusinessError444(String tag, T errorModel);

    /**
     * Status Code 500
     */
    public void onServerSideError500(String tag, T errorModel);

    /**
     * Status Code 503
     */
    public void onMaintenance503(String tag, T errorModel);

    /**
     * Status Code 600
     */
    public void onNoInternet600(String tag);
}
