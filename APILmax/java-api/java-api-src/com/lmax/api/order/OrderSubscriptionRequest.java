package com.lmax.api.order;

import com.lmax.api.SubscriptionRequest;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Request to subscribe to Order events.
 */
public class OrderSubscriptionRequest extends SubscriptionRequest
{
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
                value("type", "order").
                endElement("subscription").
                endElement("body").
                endElement("req");
    }
}
