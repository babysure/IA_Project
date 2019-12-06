package com.lmax.api.account;

import com.lmax.api.SubscriptionRequest;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Subscription request for all account information.
 */
public class AccountSubscriptionRequest extends SubscriptionRequest
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
                       value("type", "account").
                       endElement("subscription").
                   endElement("body").
               endElement("req");
    }
}
