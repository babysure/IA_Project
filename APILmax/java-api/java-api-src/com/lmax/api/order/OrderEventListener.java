package com.lmax.api.order;

/**
 * Asynchronous listener for ALL amendments to the state of your orders.
 */
public interface OrderEventListener
{
    /**
     * Called when an execution is received.
     *
     * @param order the Order
     */
    void notify(Order order);
}
