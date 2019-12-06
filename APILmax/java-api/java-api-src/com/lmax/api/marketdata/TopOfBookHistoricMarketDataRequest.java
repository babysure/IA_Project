package com.lmax.api.marketdata;

import com.lmax.api.internal.xml.StructuredWriter;

import java.util.Date;

/**
 * Request historic order book prices and quantities.
 */
public class TopOfBookHistoricMarketDataRequest implements HistoricMarketDataRequest
{
    private static final int DEPTH = 1;

    private final String instructionId;
    private final long instrumentId;
    private final Date from;
    private final Date to;
    private final Format format;

    /**
     * Request historic prices and quantities for the given order book.
     *
     * @param instructionId Unique ID for this request
     * @param instrumentId  The ID of the instrument to return the data for
     * @param from          The date and time of the start of the range
     * @param to            The date and time of the end of the range
     * @param format        Protocol e.g CSV
     */
    public TopOfBookHistoricMarketDataRequest(final String instructionId, final long instrumentId, final Date from, final Date to, final Format format)
    {
        this.instructionId = instructionId;
        this.instrumentId = instrumentId;
        this.from = from;
        this.to = to;
        this.format = format;

    }

     /**
     * Get the URI for the request.
     *
     * @return the URI.
     */
    @Override
    public String getUri()
    {
        return "/secure/read/marketData/requestHistoricMarketData";
    }

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
                value("instructionId", instructionId).
                value("orderBookId", instrumentId).
                value("from", from.getTime()).
                value("to", to.getTime()).
                startElement("orderBook").
                    startElement("options").
                        value("option", "BID").
                        value("option", "ASK").
                    endElement("options").
                    value("depth", DEPTH).
                    value("format", format.name()).
                endElement("orderBook").
            endElement("body").
        endElement("req");
    }
}
