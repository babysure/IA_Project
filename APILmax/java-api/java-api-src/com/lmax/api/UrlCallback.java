package com.lmax.api;

import java.io.InputStream;
import java.net.URL;

/**
* Called back when a request for open url succeeds or fails.
*/
public interface UrlCallback
{
    /**
     * Return the url and InputStream, when successful.
     *
     * @param url The url opened.
     * @param inputStream The InputStream of the opened url.
     */
    void onSuccess(URL url, InputStream inputStream);

    /**
     * Called when failure occurs.
     *
     * @param failureResponse Contains information about
     * the failure.
     */
    void onFailure(FailureResponse failureResponse);
}
