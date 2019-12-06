package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;

/**
 * The results of a pass of the order through the exchange.
 */
public interface Execution
{
    /**
     * An identifier for the matching cycle in which this order was processed.
     * This identifier is unlikely to be useful, it doesn't uniquely
     * identify the execution, but identified the matching cycle.  If you
     * want to correlate trades with the UI and FIX use {@link #getEncodedExecutionId()}.
     *
     * @return the execution.
     */
    long getExecutionId();

    /**
     * The price at which the execution took place.
     * @return the Price.
     */
    FixedPointNumber getPrice();

    /**
     * The size of execution.
     * @return the quantity.
     */
    FixedPointNumber getQuantity();

    /**
     * The order which the execution effected.  Note that the order object
     * proved by this method is the state of the order at the end of the
     * matching cycle.  It won't the state of the order directly after
     * this execution.  I.e. it doesn't work the same way fix does.
     * @return the order.
     */
    Order getOrder();

    /**
     * The quantity of the order which was cancelled in this execution.
     * @return the Cancelled quantity. 
     */
    FixedPointNumber getCancelledQuantity();

    /**
     * Encoded execution id for a trade, can be used to correlate with the secondary
     * execution id received from broker fix and execution id from the activity log
     * in the web trading UI.
     *
     * @return the encoded execution id or null if this execution is not a trade.
     */
    String getEncodedExecutionId();
}
