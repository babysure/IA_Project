package com.lmax.api.account;

import com.lmax.api.FailureResponse;
import com.lmax.api.Session;

/**
 * Asynchronous listener for login responses.
 */
public interface LoginCallback
{
    /**
     * Called when the connection was successfully authenticated.
     * @param session a session to use when interacting with LMAX
     */
    void onLoginSuccess(Session session);

    /**
     * Called when a login failed for any reason.
     * @param failureResponse information on the failure
     */
    void onLoginFailure(FailureResponse failureResponse);
}
