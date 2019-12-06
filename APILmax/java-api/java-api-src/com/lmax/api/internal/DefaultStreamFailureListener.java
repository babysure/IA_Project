package com.lmax.api.internal;

import com.lmax.api.StreamFailureListener;

public class DefaultStreamFailureListener implements StreamFailureListener
{
    @Override
    @SuppressWarnings("checkstyle:regexpsinglelinejava")
    public void notifyStreamFailure(Exception exception)
    {
        System.err.println(exception);
    }
}
