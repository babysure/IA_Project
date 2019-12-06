package com.lmax.api.orderbook;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * An instrument search request.
 */
public class SearchInstrumentRequest implements Request
{

    private final String queryString;
    private final long offsetInstrumentId;

    /**
     * Construct a request for a security definition.  Uses a query string that
     * provides flexible query mechanism.  There are 2 main forms of the query string
     * to find a specific instrument the "id: (instrumentId)" form can be used.
     * To do a general search, use a term such as "CURRENCY", which will find
     * all of the currency instruments.  A search term like "UK" will find all
     * of the instruments that have "UK" in the name.

     * @param queryString the query string.
     */
    public SearchInstrumentRequest(final String queryString)
    {
        this.queryString = queryString;
        this.offsetInstrumentId = 0;
    }

    /**
     * Construct a request for a security definition.  Uses a query string that
     * provides flexible query mechanism.  There are 2 main forms of the query string
     * to find a specific instrument the "id: (instrumentId)" form can be used.
     * To do a general search, use a term such as "CURRENCY", which will find
     * all of the currency instruments.  A search term like "UK" will find all
     * of the instruments that have "UK" in the name.

     * @param queryString the query string.
     * @param offsetInstrumentId last instrument id returned in alphabetical order.
     */
    public SearchInstrumentRequest(final String queryString, long offsetInstrumentId)
    {
        this.queryString = queryString;
        this.offsetInstrumentId = offsetInstrumentId;
    }

    /**
     * The URI to send the instrument search request to.
     *
     * @return The URI to send the instrument search request to.
     */
    @Override
    public String getUri()
    {
        try
        {
            final StringBuilder buffer = new StringBuilder();

            buffer.append("/secure/instrument/searchCurrentInstruments?q=").append(URLEncoder.encode(queryString, "UTF-8")).append("&offset=").append(offsetInstrumentId);

            return buffer.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Internal: Output this request.
     * 
     * @param writer The destination for the content of this request
     */
    @Override
    public void writeTo(StructuredWriter writer)
    {
        throw new UnsupportedOperationException("This is a GET request");
    }
}
