package com.lmax.api.heartbeat;

/**
 * Interface called whenever a heartbeat event is received 
 * via the stream interface.
 */
public interface HeartbeatEventListener
{
    /**
     * Called whenever a heartbeat event occurs.
     * 
     * @param accountId The account that requested the heartbeat
     * @param token The user specified token
     */
    void notify(long accountId, String token);
}
