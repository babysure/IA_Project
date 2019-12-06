package com.lmax.api.position;

/**
 *  Asynchronous listener for Position updates.
 */
public interface PositionEventListener
{
    /**
     * Called when the system notifies us of a position change.
     *
     * @param positionEvent the event.
     */
    void notify(PositionEvent positionEvent);
}
