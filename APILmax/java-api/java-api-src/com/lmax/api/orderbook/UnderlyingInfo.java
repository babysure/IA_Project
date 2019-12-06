package com.lmax.api.orderbook;

/**
 * The underlying asset of the traded instrument, contain information
 * such as symbol, asset class etc.
 */
public interface UnderlyingInfo
{
    /**
     * Get the text symbol used for the instrument.
     *
     * @return symbol.
     */
    String getSymbol();

    /**
     * Get the ISIN of the underlying instrument.
     *
     * @return isin.
     */
    String getIsin();

    /**
     * Get the asset class of the underlying.
     *
     * @return asset class.
     */
    String getAssetClass();
}
