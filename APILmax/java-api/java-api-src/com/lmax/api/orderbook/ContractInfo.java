package com.lmax.api.orderbook;

import com.lmax.api.FixedPointNumber;

/**
 * Holds the information about the contract for this instrument.
 */
public interface ContractInfo
{
    /**
     * Get the currency this instrument is traded in.
     *
     * @return currency.
     */
    String getCurrency();

    /**
     * Get the price for a single contract unit.
     *
     * @return unit price.
     */
    FixedPointNumber getUnitPrice();

    /**
     * Get the name of the units being traded, e.g. barrels of oil, US dollars
     *
     * @return unit of measure.
     */
    String getUnitOfMeasure();

    /**
     * Get the contract size.
     *
     * @return contract size.
     */
    FixedPointNumber getContractSize();
}
