package com.lmax.api.orderbook;

import com.lmax.api.SubscriptionRequest;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Request to subscribe to order book events for an instrument.
 */
public class OrderBookSubscriptionRequest extends SubscriptionRequest
{
    private final long instrumentId;

    /**
     * Construct a new OrderBookSubscriptionRequest.
     *
     * @param instrumentId The id of the instrument to subscribe to order book events for.
     */
    public OrderBookSubscriptionRequest(final long instrumentId)
    {
        this.instrumentId = instrumentId;
    }

    /**
     * Get the instrumentId.
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
                           value("ob2", instrumentId).
                       endElement("subscription").
                   endElement("body").
               endElement("req");
    }
}
