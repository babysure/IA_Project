package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

public class AmendLimitPrice implements Request
{
    private final long instrumentId;
    private final String originalInstructionId;
    private final String instructionId;
    private final FixedPointNumber newPrice;

    public AmendLimitPrice(
            final long instrumentId,
            final String originalInstructionId,
            final String instructionId,
            final FixedPointNumber newPrice)
    {
        this.instrumentId = instrumentId;
        this.originalInstructionId = originalInstructionId;
        this.instructionId = instructionId;
        this.newPrice = newPrice;
    }

    @Override
    public String getUri()
    {
        return "/secure/trade/amendOrder";
    }

    @Override
    public void writeTo(final StructuredWriter writer)
    {
        writer
                .startElement("req")
                .startElement("body")
                .value("instrumentId", instrumentId)
                .value("originalInstructionId", originalInstructionId)
                .value("instructionId", instructionId)
                .value("limitPrice", newPrice)
                .endElement("body")
                .endElement("req");
    }
}
