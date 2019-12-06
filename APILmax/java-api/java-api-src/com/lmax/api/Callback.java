package com.lmax.api;

/**
 * Notifies the success or failure of the Request.
 */
public interface Callback
{
    /**
     * Called when the request has been successfully received by LMAX Broker.
     */
    void onSuccess();

    /**
     * Called when the request has been rejected or not received by LMAX Broker
     *
     * @param failureResponse information on the failure
     */
    void onFailure(FailureResponse failureResponse);
}
