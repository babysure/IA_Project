package com.lmax.api.order;

import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Cancel Order Request.
 */
public class CancelOrderRequest implements Request
{
    private final long instrumentId;
    private final String originalInstructionId;
    private final String instructionId;

    /**
     * Construct the cancel order request.
     *
     * @param instrumentId          The instrumentId of the order book on which, the order to be cancelled was placed.
     * @param originalInstructionId The originalInstructionId that was returned by the place order response
     *                              callback.
     */
    public CancelOrderRequest(long instrumentId, String originalInstructionId)
    {
        this(instrumentId, originalInstructionId, null);
    }

    /**
     * Construct the cancel order request.
     *
     * @param instrumentId          The instrumentId of the order book on which, the order to be cancelled was placed.
     * @param originalInstructionId The originalInstructionId that was returned by the place order response
     *                              callback.
     * @param instructionId         The user-defined correlation ID of the cancellation request
     */
    public CancelOrderRequest(long instrumentId, String originalInstructionId, String instructionId)
    {
        this.instrumentId = instrumentId;
        this.originalInstructionId = originalInstructionId;
        this.instructionId = instructionId;
    }

    /**
     * The cancel order uri.
     *
     * @return The cancel order uri.
     */
    @Override
    public String getUri()
    {
        return "/secure/trade/cancel";
    }

    /**
     * Internal: Output this request.
     * 
     * @param writer The destination for the content of this request
     */
    public void writeTo(StructuredWriter writer)
    {
        writer.startElement("req").
                   startElement("body").
                       value("instrumentId", instrumentId).
                       value("originalInstructionId", originalInstructionId).
                       valueOrNone("instructionId", instructionId).
                   endElement("body").
               endElement("req");
    }
}
