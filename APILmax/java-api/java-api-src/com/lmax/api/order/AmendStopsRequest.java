package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Request to amend stop loss/profit on an existing order.
 */
public class AmendStopsRequest implements Request
{
    private static final String STOP_LOSS_OFFSET_TAG_NAME = "stopLossOffset";
    private static final String STOP_PROFIT_OFFSET_TAG_NAME = "stopProfitOffset";
    
    private final long instrumentId;
    private final String originalInstructionId;
    private final String instructionId;
    private final FixedPointNumber stopLossOffset;
    private final String stopLossInstructionId;
    private final FixedPointNumber stopProfitOffset;
    private final boolean trailingStop;
    private final String stopProfitInstructionId;


    /**
     * Construct an AmendStopLossProfitRequest using the instrument id and the instruction id
     * of the original order.
     *
     * @param instrumentId          The instrument id that the original order was placed on.
     * @param originalInstructionId The instructionId id that the original order.
     * @param instructionId         The user-defined correlation ID of the amend stops request
     * @param stopLossOffset        The new stop loss offset, use null to indicate the value should be removed.
     * @param stopProfitOffset      The new stop profit offset, use null to indicate the value should be removed.
     * @param trailingStop          Specifies if the stop loss contingent order is trailing.
     * @param stopProfitInstructionId The instructionId that will be used to identify the stop profit order.
     * @param stopLossInstructionId The instructionId that will be used to identify the stop loss order.
     */
    public AmendStopsRequest(final long instrumentId, final String originalInstructionId, final String instructionId,
                             final FixedPointNumber stopLossOffset, final boolean trailingStop, String stopLossInstructionId,
                             final FixedPointNumber stopProfitOffset, String stopProfitInstructionId)
    {
        this.instrumentId = instrumentId;
        this.originalInstructionId = originalInstructionId;
        this.instructionId = instructionId;
        this.stopLossOffset = stopLossOffset;
        this.stopLossInstructionId = stopLossInstructionId;
        this.stopProfitOffset = stopProfitOffset;
        this.trailingStop = trailingStop;
        this.stopProfitInstructionId = stopProfitInstructionId;
    }

    /**
     * Construct an AmendStopLossProfitRequest using the instrument id and the instruction id
     * of the original order.
     *
     * @param instrumentId          The instrument id that the original order was placed on.
     * @param originalInstructionId The instructionId id that the original order.
     * @param instructionId         The user-defined correlation ID of the amend stops request
     * @param stopLossOffset        The new stop loss offset, use null to indicate the value should be removed.
     * @param stopProfitOffset      The new stop profit offset, use null to indicate the value should be removed.
     * @param trailingStop          Specifies if the stop loss contingent order is trailing.
     */
    public AmendStopsRequest(final long instrumentId, final String originalInstructionId, final String instructionId, final FixedPointNumber stopLossOffset, final boolean trailingStop,
                             final FixedPointNumber stopProfitOffset)
    {
        this(instrumentId, originalInstructionId, instructionId, stopLossOffset, trailingStop, null, stopProfitOffset, null);
    }

    /**
     * Construct an AmendStopLossProfitRequest using the instrument id and the instruction id
     * of the original order.
     *
     * @param instrumentId          The instrument id that the original order was placed on.
     * @param originalInstructionId The instructionId id that the original order.
     * @param instructionId         The user-defined correlation ID of the amend stops request
     * @param stopLossOffset        The new stop loss offset, use null to indicate the value should be removed.
     * @param stopProfitOffset      The new stop profit offset, use null to indicate the value should be removed.
     */
    public AmendStopsRequest(final long instrumentId, final String originalInstructionId, final String instructionId, final FixedPointNumber stopLossOffset, final FixedPointNumber stopProfitOffset)
    {
        this(instrumentId, originalInstructionId, instructionId, stopLossOffset, false, stopProfitOffset);
    }

    /**
     * Construct an AmendStopLossProfitRequest using the instrument id and the instruction id
     * of the original order.
     *
     * @param instrumentId          The instrument id that the original order was placed on.
     * @param originalInstructionId The instructionId id that the original order.
     * @param stopLossOffset        The new stop loss offset, use null to indicate the value should be removed.
     * @param stopProfitOffset      The new stop profit offset, use null to indicate the value should be removed.
     * @param stopProfitInstructionId The instructionId that will be used to identify the stop profit order.
     * @param stopLossInstructionId The instructionId that will be used to identify the stop loss order.
     */
    public AmendStopsRequest(final long instrumentId, final String originalInstructionId, final FixedPointNumber stopLossOffset, final String stopLossInstructionId,
                             final FixedPointNumber stopProfitOffset, final String stopProfitInstructionId)
    {
        this(instrumentId, originalInstructionId, null, stopLossOffset, false, stopLossInstructionId, stopProfitOffset, stopProfitInstructionId);
    }

    /**
     * Construct an AmendStopLossProfitRequest using the instrument id and the instruction id
     * of the original order.
     *
     * @param instrumentId          The instrument id that the original order was placed on.
     * @param originalInstructionId The instructionId id that the original order.
     * @param stopLossOffset        The new stop loss offset, use null to indicate the value should be removed.
     * @param stopProfitOffset      The new stop profit offset, use null to indicate the value should be removed.
     */
    public AmendStopsRequest(final long instrumentId, final String originalInstructionId, final FixedPointNumber stopLossOffset, final FixedPointNumber stopProfitOffset)
    {
        this(instrumentId, originalInstructionId, null, stopLossOffset, stopProfitOffset);
    }

    /**
     * The amend stops uri.
     *
     * @return the amend stops uri
     */
    @Override
    public String getUri()
    {
        return "/secure/trade/amendOrder";
    }

    /**
     * Internal: Output this request.
     * 
     * @param writer The destination for the content of this request
     */
    public void writeTo(StructuredWriter writer)
    {
        if (trailingStop)
        {
            writer.startElement("req").
                startElement("body").
                value("instrumentId", instrumentId).
                    value("originalInstructionId", originalInstructionId).
                valueOrNone("instructionId", instructionId).
                startElement("stopLossOrder").
                    value("offset", stopLossOffset).
                    value("trailing", trailingStop).
                    endElement("stopLossOrder").
                valueOrNone("stopLossInstructionId", stopLossInstructionId).
                value(STOP_PROFIT_OFFSET_TAG_NAME, stopProfitOffset).
                valueOrNone("stopProfitInstructionId", stopProfitInstructionId).
                endElement("body").
                endElement("req");
        }
        else
        {
            writer.startElement("req").
                    startElement("body").
                value("instrumentId", instrumentId).
                value("originalInstructionId", originalInstructionId).
                    valueOrNone("instructionId", instructionId).
                    value(STOP_LOSS_OFFSET_TAG_NAME, stopLossOffset).
                valueOrNone("stopLossInstructionId", stopLossInstructionId).
                value(STOP_PROFIT_OFFSET_TAG_NAME, stopProfitOffset).
                valueOrNone("stopProfitInstructionId", stopProfitInstructionId).
                endElement("body").
                endElement("req");
        }
    }
}
