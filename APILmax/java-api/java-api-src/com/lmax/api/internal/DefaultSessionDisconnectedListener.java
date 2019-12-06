package com.lmax.api.internal;

import com.lmax.api.SessionDisconnectedListener;

public class DefaultSessionDisconnectedListener implements SessionDisconnectedListener
{
    @Override
    @SuppressWarnings("checkstyle:regexpsinglelinejava")
    public void notifySessionDisconnected()
    {
        System.err.println("Session disconnected");
    }
}
