package com.lmax.api.orderbook;

/**
 * Represents the current status of the Order Book.
 */
public class OrderBookStatusEvent
{
    private final long instrumentId;
    private final OrderBookStatus orderBookStatus;

    /**
     * For internal use only.
     * @param instrumentId The instrument id
     * @param orderBookStatus The status
     */
    public OrderBookStatusEvent(final long instrumentId, final OrderBookStatus orderBookStatus)
    {
        this.instrumentId = instrumentId;
        this.orderBookStatus = orderBookStatus;
    }

    /**
     * The instrument id this event applies to.
     *
     * @return order book id
     */
    public long getInstrumentId()
    {
        return instrumentId;
    }

    /**
     * The current status.
     *
     * @return status
     */
    public OrderBookStatus getStatus()
    {
        return orderBookStatus;
    }
}
