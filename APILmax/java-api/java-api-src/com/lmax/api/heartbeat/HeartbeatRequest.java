package com.lmax.api.heartbeat;

import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * A request for a heartbeat from the Lmax Trader.
 */
public class HeartbeatRequest implements Request
{
    private String token;

    /**
     * Create a heartbeat request with a user-specified
     * token.
     *
     * @param token User specified token that will be
     * echo back to the user.
     */
    public HeartbeatRequest(String token)
    {
        this.token = token;
    }

    /**
     * Set the user specified token for the heartbeat.
     *
     * @param token User specified token that will be
     * echo back to the user.
     */
    public void setToken(String token)
    {
        this.token = token;
    }

    /**
     * The URI to send the heartbeat request to.
     *
     * @return The URI to send the heartbeat request to.
     */
    @Override
    public String getUri()
    {
        return "/secure/read/heartbeat";
    }

    /**
     * Internal: Output this request.
     *
     * @param writer The destination for the content of this request
     */
    @Override
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").
                   startElement("body").
                       value("token", token).
                   endElement("body").
               endElement("req");
    }
}
