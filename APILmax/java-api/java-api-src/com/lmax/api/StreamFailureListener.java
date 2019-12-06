package com.lmax.api;


/**
 * Listener interface for failures on the event stream.  Implementations can
 * use this listener interface to make the appropriate decision when there
 * is some sort of failure on the event stream, e.g. connection dropped.
 */
public interface StreamFailureListener
{
    /**
     * Called each time there is an exception on the event stream.  The implementer
     * can make a decision as to the appropriate action within this method.  E.g.
     * <code>session.stop()</code> could be called if the user wanted the system
     * to stop processing events.  By default the stream handler retry the connection
     * immediately.
     * 
     * @param exception The exception while processing events
     */
    void notifyStreamFailure(Exception exception);
}
