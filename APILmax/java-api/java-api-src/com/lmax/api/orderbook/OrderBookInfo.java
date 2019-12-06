package com.lmax.api.orderbook;

import com.lmax.api.FixedPointNumber;

/**
 * Holds information related to the behaviour of the order book.
 */
public interface OrderBookInfo
{
    /**
     * Get the price increment in which orders can be placed, i.e. the
     * tick size.
     *
     * @return price increment.
     */
    FixedPointNumber getPriceIncrement();

    /**
     * Get the quantity increment in which orders can be placed.
     *
     * @return quantity increment.
     */
    FixedPointNumber getQuantityIncrement();

    /**
     * Get the retail volatility band for the order book, this limits how
     * far from the spread an order can be placed.
     *
     * @return volatility band.
     */
    FixedPointNumber getVolatilityBandPercentage();
}
