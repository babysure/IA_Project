package com.lmax.api.marketdata;

import com.lmax.api.internal.Request;

/**
 * Marker interface for the different types of requests for historic market data.
 */
public interface HistoricMarketDataRequest extends Request
{
    /**
     * The time period the data will be aggregated over.
     */
    enum Resolution
    {
        MINUTE,
        DAY
    }

    /**
     * The format the historic market data is returned in.
     */
    enum Format
    {
        CSV
    }
}
