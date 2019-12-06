package com.lmax.api.orderbook;

import com.lmax.api.SubscriptionRequest;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Request to subscribe to order book status events for an order book (i.e. Opened, Suspended, Closed, etc).
 */
public class OrderBookStatusSubscriptionRequest extends SubscriptionRequest
{
    private final long instrumentId;

    /**
     * Construct a new OrderBookStatusSubscriptionRequest.
     *
     * @param instrumentId The id of the instrument to subscribe to status events for.
     */
    public OrderBookStatusSubscriptionRequest(final long instrumentId)
    {
        this.instrumentId = instrumentId;
    }

    /**
     * Get the instrument id.
     *
     * @return the instrumentId
     */
    public long getInstrumentId()
    {
        return instrumentId;
    }
    
    /**
     * Internal: Output this request.
     * 
     * @param writer The destination for the content of this request
     */
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").
                   startElement("body").
                       startElement("subscription").
                           value("orderBookStatus", instrumentId).
                       endElement("subscription").
                   endElement("body").
               endElement("req");
    }
}
