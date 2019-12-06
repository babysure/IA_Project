package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.TimeInForce;
import com.lmax.api.internal.Request;


/**
 * Order Request.
 */
public interface OrderSpecification extends Request
{
    /**
     * Set the user correlation ID for this order.  This must be unique for each new order that is placed,
     * and will be the instructionId needed if the order is to be cancelled or amended.
     *
     * @param instructionId The ID to associate with this order.
     */
    void setInstructionId(final String instructionId);

    /**
     * Set the quantity of the order specification to the specified value.
     * This allows you to place multiple orders with different quantities using one order specification.
     *
     * @param quantity New quantity for the order specification.
     */
    void setQuantity(FixedPointNumber quantity);

    /**
     * Set the stop loss price offset for the order.
     *
     * @param stopLossPriceOffset if set to null no stop loss will be in force.
     */
    void setStopLossPriceOffset(FixedPointNumber stopLossPriceOffset);

    /**
     * Set the stop profit price offset for the order.
     *
     * @param stopProfitPriceOffset if set to null no stop profit will be in force.
     */
    void setStopProfitPriceOffset(FixedPointNumber stopProfitPriceOffset);

    /**
     * Set the time in force policy for the order.
     *
     * @param timeInForce The time in force policy for the order.
     */
    void setTimeInForce(TimeInForce timeInForce);

    /**
     * Set the instrument for the order.
     *
     * @param instrumentId The instrument id for the order.
     */
    void setInstrumentId(long instrumentId);

    /**
     * If a stop is set for the order, set whether or not it is trailing.
     *
     * @param trailingStop Whether or not the stop on this order should trail.
     */
    void setTrailingStop(boolean trailingStop);
}
