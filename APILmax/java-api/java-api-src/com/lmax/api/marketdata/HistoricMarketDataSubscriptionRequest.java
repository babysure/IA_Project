package com.lmax.api.marketdata;

import com.lmax.api.SubscriptionRequest;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Subscription request for historic market data events.
 */
public class HistoricMarketDataSubscriptionRequest extends SubscriptionRequest
{
    /**
     * Internal: Output this request.
     *
     * @param writer The destination for the content of this request
     */
    @Override
    public void writeTo(final StructuredWriter writer)
    {
        writer.startElement("req").
                   startElement("body").
                       startElement("subscription").
                       value("type", "historicMarketData").
                       endElement("subscription").
                   endElement("body").
               endElement("req");
    }
}
