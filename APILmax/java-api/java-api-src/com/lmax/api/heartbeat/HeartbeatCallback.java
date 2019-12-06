package com.lmax.api.heartbeat;

import com.lmax.api.FailureResponse;

/**
 * Called back when a request for heartbeat succeeds or fails.
 */
public interface HeartbeatCallback
{
    /**
     * Return the user specified token, when successful.
     * 
     * @param token The user specified token.
     */
    void onSuccess(String token);

    /**
     * Called when failure occurs.
     * 
     * @param failureResponse Contains information about
     * the failure.
     */
    void onFailure(FailureResponse failureResponse);
}
