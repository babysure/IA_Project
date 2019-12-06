package com.lmax.api.orderbook;

/**
 *  Asynchronous listener for OrderBookStatus responses.
 */
public interface OrderBookStatusEventListener
{
    /**
     * Called when the system notifies us of an order book status change.
     *
     * @param orderBookStatusEvent the event.
     */
    void notify(OrderBookStatusEvent orderBookStatusEvent);
}
