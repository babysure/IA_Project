package com.lmax.api.orderbook;

import com.lmax.api.FixedPointNumber;

/**
 * Holds the Risk elements for the instrument.
 */
public interface RiskInfo
{
    /**
     * Get the margin rate as a percentage for this instrument.
     *
     * @return margin rate.
     */
    FixedPointNumber getMarginRate();

    /**
     * Get the maximum position that can be held by a retail user on
     * this instrument.
     *
     * @return maximum position.
     */
    FixedPointNumber getMaximumPosition();
}
