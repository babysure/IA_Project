package com.lmax.api.position;

import com.lmax.api.FixedPointNumber;

/**
 * Represents a change to a position held by the account.
 */
public interface PositionEvent
{
    /**
     * Returns the account ID that the position belongs to.
     * @return the account ID that the position belongs to.
     */
    long getAccountId();

    /**
     * Returns the instrument ID that the position pertains to.
     * @return the instrument ID that the position pertains to.
     */
    long getInstrumentId();

    /**
     * Returns the valuation of the position.
     * @return the valuation of the position.
     */
    FixedPointNumber getValuation();

    /**
     * Returns the short unfilled cost.
     * @return the short unfilled cost.
     */
    FixedPointNumber getShortUnfilledCost();

    /**
     * Returns the long unfilled cost.
     * @return the long unfilled cost.
     */
    FixedPointNumber getLongUnfilledCost();

    /**
     * Returns the open quantity of the position.
     * @return the open quantity of the position.
     */
    FixedPointNumber getOpenQuantity();

    /**
     * Returns the cumulative cost of the position.
     * @return the cumulative cost of the position.
     */
    FixedPointNumber getCumulativeCost();

    /**
     * Returns the open cost of the position.
     * @return the open cost of the position.
     */
    FixedPointNumber getOpenCost();
}
