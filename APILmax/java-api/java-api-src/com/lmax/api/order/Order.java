package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;

/**
 * The order.
 */
public interface Order
{
    /**
     * The id of the client correlation ID of the last modification to this order.
     *
     * @return the Instruction Id
     */
    String getInstructionId();

    /**
     * The id of the instruction which placed the order.
     *
     * @return the Original Instruction Id
     */
    String getOriginalInstructionId();

    /**
     * The id of the order.
     *
     * @return the id of the order
     */
    String getOrderId();

    /**
     * The id of the instrument on which this order was placed.
     *
     * @return the instrument Id
     */
    long getInstrumentId();

    /**
     * The if of the account which placed the order.
     *
     * @return the account id
     */
    long getAccountId();

    /**
     * Denotes the type of the of the order.
     *
     * @return the order type
     */
    OrderType getOrderType();

    /**
     * Denotes the lifespan of the the order.
     * 
     * @return the time in force for the order
     */
    TimeInForce getTimeInForce();

    /**
     * The total quantity of the order.
     *
     * @return the quantity of the order
     */
    FixedPointNumber getQuantity();

    /**
     * The amount of the order which has been filled (matched).
     *
     * @return the current filled quantity
     */
    FixedPointNumber getFilledQuantity();

    /**
     * Return the limit price of the order.
     *
     * @return limit price or <code>null</code> if the order type is not Limit.
     */
    FixedPointNumber getLimitPrice();

    /**
     * The price at which the stop loss and stop profit offsets are considered relative too.
     *
     * @return The price stops are considered relative too.
     */
    FixedPointNumber getStopReferencePrice();

    /**
     * The distance from the stop reference price at which the stop Loss Order will be placed.
     *
     * @return The size of the offset from the stop reference price.
     */
    FixedPointNumber getStopLossOffset();

    /**
     * Denotes whether the stop loss is of a trailing nature.
     *
     * @return Whether the stop loss price is trailing price changes, or is at a fixed level.
     */
    boolean hasTrailingStop();

    /**
     * The distance from the stop reference price at which the stop Profit Order will rest on the book.
     *
     * @return The size of the offset from the stop reference price.
     */
    FixedPointNumber getStopProfitOffset();

    /**
     * The amount of the order which has been cancelled.
     *
     * @return The quantity of the order which has been cancelled.
     */
    FixedPointNumber getCancelledQuantity();

    /**
     * The total commission charged on the order.
     *
     * @return The commission charged.
     */
    FixedPointNumber getCommission();
}
