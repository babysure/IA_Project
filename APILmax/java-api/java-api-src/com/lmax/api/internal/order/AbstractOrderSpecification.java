package com.lmax.api.internal.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;
import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;
import com.lmax.api.order.OrderSpecification;

public abstract class AbstractOrderSpecification implements Request, OrderSpecification
{
    private long instrumentId;
    private String instructionId;
    private FixedPointNumber quantity;
    private FixedPointNumber stopLossPriceOffset;
    private boolean trailingStop;
    private FixedPointNumber stopProfitPriceOffset;

    private TimeInForce timeInForce;

    protected AbstractOrderSpecification(long instrumentId, String instructionId, final TimeInForce timeInForce, FixedPointNumber quantity, final FixedPointNumber stopLossPriceOffset,
                                         final FixedPointNumber stopProfitPriceOffset, final boolean trailingStop)
    {
        this.instrumentId = instrumentId;
        this.instructionId = instructionId;
        if (timeInForce == TimeInForce.UNKNOWN)
        {
            throw new IllegalArgumentException("Cannot specify UNKNOWN as time in force.");
        }

        this.timeInForce = timeInForce;
        this.quantity = quantity;
        this.stopLossPriceOffset = stopLossPriceOffset;
        this.stopProfitPriceOffset = stopProfitPriceOffset;
        this.trailingStop = trailingStop;
    }

    @Override
    public void setTrailingStop(final boolean trailingStop)
    {
        this.trailingStop = trailingStop;
    }

    @Override
    public void setInstructionId(final String instructionId)
    {
        this.instructionId = instructionId;
    }

    @Override
    public void setInstrumentId(final long instrumentId)
    {
        this.instrumentId = instrumentId;
    }

    @Override
    public void setQuantity(final FixedPointNumber quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public void setStopLossPriceOffset(final FixedPointNumber stopLossPriceOffset)
    {
        this.stopLossPriceOffset = stopLossPriceOffset;
    }

    @Override
    public void setStopProfitPriceOffset(final FixedPointNumber stopProfitPriceOffset)
    {
        this.stopProfitPriceOffset = stopProfitPriceOffset;
    }

    @Override
    public void setTimeInForce(final TimeInForce timeInForce)
    {
        if (timeInForce == TimeInForce.UNKNOWN)
        {
            throw new IllegalArgumentException("Cannot specify UNKNOWN as time in force.");
        }

        this.timeInForce = timeInForce;
    }

    /**
     * Convert this request to XML.
     */
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").
            startElement("body").
            startElement("order").
                value("instrumentId", instrumentId).
                valueOrNone("instructionId", instructionId);

                writeOrderSpecificTagsTo(writer);

                writer.value("quantity", quantity).
                value("timeInForce", timeInForce.asCamelCase());

                writeStopLossOrderTo(writer);

                writer.valueOrNone("stopProfitOffset", stopProfitPriceOffset).
            endElement("order").
            endElement("body").
        endElement("req");
    }

    private void writeStopLossOrderTo(final StructuredWriter writer)
    {
        if(stopLossPriceOffset != null)
        {
            writer.startElement("stopLossOrder").
                value("offset", stopLossPriceOffset).
                value("trailing", trailingStop).
            endElement("stopLossOrder");
        }
    }

    protected abstract void writeOrderSpecificTagsTo(StructuredWriter writer);

    /**
     * The place order URI.
     *
     * @return The place order URI.
     */
    @Override
    public String getUri()
    {
        return "/secure/trade/placeOrder";
    }

    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("instrumentId=").append(instrumentId)
            .append(", instructionId=").append(instructionId)
            .append(", quantity=").append(quantity)
            .append(", stopLossPriceOffset=").append(stopLossPriceOffset)
            .append(", trailingStop=").append(trailingStop)
            .append(", stopProfitPriceOffset=").append(stopProfitPriceOffset)
            .append(", timeInForce=").append(timeInForce.asCamelCase())
            .toString();
    }
}
