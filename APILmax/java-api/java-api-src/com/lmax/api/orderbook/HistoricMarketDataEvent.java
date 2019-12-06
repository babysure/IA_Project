package com.lmax.api.orderbook;

import java.net.URL;
import java.util.List;

/**
 * A event that contains requested historic market data for an instrument.
 */
public interface HistoricMarketDataEvent
{
    /**
     * The instruction ID supplied with the request for historic market data.
     *
     * @return instruction id.
     */
    String getInstructionId();

    /**
     * The URLs to the historic market data. Note that the request must be
     * authenticated to download this data by supplying the session cookie.
     *
     * @return the list of URLs to the historic market data.
     */
    List<URL> getUrls();
}
