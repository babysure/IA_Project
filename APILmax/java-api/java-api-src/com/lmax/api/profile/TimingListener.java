package com.lmax.api.profile;

/**
 * Listener for timing events.
 */
public interface TimingListener
{
    /**
     * Callback when a timing event occurs.
     * 
     * @param name A symbolic name for the timing event.
     * @param elapsedTimeNanos The time taken for the callback to complete.
     */
    void notify(String name, long elapsedTimeNanos);
}
