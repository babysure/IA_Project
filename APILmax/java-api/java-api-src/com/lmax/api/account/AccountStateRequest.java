package com.lmax.api.account;

import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * A request for an AccountStateEvent.
 */
public class AccountStateRequest implements Request
{
    /**
     * The URI to send the account state request to.
     *
     * @return The URI to send the account state request to.
     */
    @Override
    public String getUri()
    {
        return "/secure/account/requestAccountState";
    }

    /**
     * Internal: Output this request.
     * 
     * @param writer The destination for the content of this request
     */
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").startElement("body").
               endElement("body").endElement("req");
    }
}
