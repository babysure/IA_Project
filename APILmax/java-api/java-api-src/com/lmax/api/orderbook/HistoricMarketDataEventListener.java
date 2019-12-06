package com.lmax.api.orderbook;

/**
 * Interface called whenever a historic market data event is received
 * via the stream interface.
 */
public interface HistoricMarketDataEventListener
{
     /**
     * Called whenever a historic market data event occurs.
     *
     * @param event The event that occurred.
     */
    void notify(HistoricMarketDataEvent event);
}
