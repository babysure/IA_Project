package com.lmax.api;


/**
 * Listener interface for notification that your session has been disconnected.
 * This may happen because the session has timed out, your account has been locked, etc.
 * Timeouts can be avoided by sending heartbeat requests every 5 minutes or so.
 */
public interface SessionDisconnectedListener
{
    /**
     * Called if the session has been disconnected.  The session can no longer be used.
     * In order to continue, a new session must be created by logging in again.
     */
    void notifySessionDisconnected();
}
