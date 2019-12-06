package com.lmax.api.orderbook;

import com.lmax.api.FixedPointNumber;

/**
 * Represents the quantity at a price.
 */
public interface PricePoint
{
    /**
     * The price.
     * @return the price
     */
    FixedPointNumber getPrice();

    /**
     * The quantity available at the given price.
     * @return quantity available at the given price
     */
    FixedPointNumber getQuantity();
}
