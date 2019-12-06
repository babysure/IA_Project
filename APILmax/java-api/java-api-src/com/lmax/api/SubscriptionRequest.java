package com.lmax.api;

import com.lmax.api.internal.Request;

/**
 * Tag interface to identify Subscription Requests.
 */
public abstract class SubscriptionRequest implements Request
{
    /**
     * The uri for this request.
     * This is for internal use only.
     *
     * @return The uri for this request.
     */
    public final String getUri()
    {
        return "/secure/subscribe";
    }
}
