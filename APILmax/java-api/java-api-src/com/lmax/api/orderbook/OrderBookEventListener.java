package com.lmax.api.orderbook;

/**
 *  Asynchronous listener for OrderBook responses.
 */
public interface OrderBookEventListener
{
    /**
     * Called when the system notifies us of an orderBook change.
     *
     * @param orderBookEvent the event.
     */
    void notify(OrderBookEvent orderBookEvent);
}
