package com.lmax.api.internal.protocol;

import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Encapsulates a request to logout of LMAX.
 */
public class LogoutRequest implements Request
{


    /**
     * Returns the path to the logout request.
     *
     * @return The path to the logout request.
     */
    @Override
    public String getUri()
    {
        return "/public/security/logout";
    }


    /**
     * Returns the logout request in xml format.
     */
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").writeEmptyTag("body").endElement("req");
    }
}
