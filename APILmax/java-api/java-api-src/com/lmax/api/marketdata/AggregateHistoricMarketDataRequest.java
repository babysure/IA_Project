package com.lmax.api.marketdata;

import java.util.Date;

import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Request aggregate historic prices and quantities.
 */
public class AggregateHistoricMarketDataRequest implements HistoricMarketDataRequest
{
    private static final int DEPTH = 1;

    private final String instructionId;
    private final long instrumentId;
    private final Date from;
    private final Date to;
    private final Format format;
    private final Resolution resolution;
    private final Option[] options;

     /**
      * Request historic prices and quantities for the given order book.
      *
      * @param instructionId Unique ID for this request
      * @param instrumentId  The ID of the instrument to return the data for
      * @param from          The date and time of the start of the range
      * @param to            The date and time of the end of the range
      * @param format        Protocol e.g CSV
      * @param resolution    Granularity - e.g. minute/day
      * @param options       The type of prices to be returned - e.g. BID/ASK/TRADE
      */
    public AggregateHistoricMarketDataRequest(final String instructionId, final long instrumentId, final Date from, final Date to, final Format format,
                                              final Resolution resolution, final Option... options)
    {
        this.instructionId = instructionId;
        this.instrumentId = instrumentId;
        this.from = from;
        this.to = to;
        this.format = format;
        this.resolution = resolution;
        this.options = options;
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
                startElement("aggregate").
                    startElement("options");
                       writeOptions(writer, options).
                    endElement("options").
                    value("resolution", resolution.name()).
                    value("depth", DEPTH).
                    value("format", format.name()).
                endElement("aggregate").
             endElement("body").
            endElement("req");
    }

    private static StructuredWriter writeOptions(final StructuredWriter writer, final Option[] options)
    {
        for (final Option option : options)
        {
            writer.value("option", option.name());
        }
        return writer;
    }

    /**
     * Defines the different types of data that can be returned.
     */
    public enum Option
    {
        BID,
        ASK
    }
}
