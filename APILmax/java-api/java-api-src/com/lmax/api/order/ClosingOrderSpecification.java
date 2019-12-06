package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Closing Order Specification.  Allows you to place an order which closes out a given quantity, either
 * against an instrument position, or against a specific order.
 */
public class ClosingOrderSpecification implements Request
{
    private static final String NO_INSTRUCTION_ID = null;

    private long instrumentId;
    private FixedPointNumber quantity;
    private String instructionId;
    private String originalInstructionId;

    /**
     * Construct a ClosingOrderSpecification.  This allows you to close a quantity of your position on a given instrument by
     * placing a market order in the opposite direction of your existing position.
     *
     * @param instructionId The user-defined correlation id
     * @param instrumentId  The id of the instrument for the order book to close position on
     * @param quantity      The quantity to close.  A signed value, where the sign indicates the side of the
     *                      market that order is placed.  Positive implies a buy order, where as negative is a sell
     */
    public ClosingOrderSpecification(final String instructionId, final long instrumentId, final FixedPointNumber quantity)
    {
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.instructionId = instructionId;
    }

    /**
     * Construct a ClosingOrderSpecification.  This constructor allows you to specify the instruction ID (originalInstructionId) of
     * a specific order to close out against, and places a market order in the opposite direction of your existing order.
     *
     * @param instructionId         The user-defined correlation id
     * @param instrumentId          The id of the instrument for the order book to close position on
     * @param originalInstructionId the instruction ID of the original order to close
     * @param quantity              The quantity to close.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     */
    public ClosingOrderSpecification(final String instructionId, final long instrumentId, final String originalInstructionId, final FixedPointNumber quantity)
    {
        this(instructionId, instrumentId, quantity);
        this.originalInstructionId = originalInstructionId;
    }

    /**
     * Set the instrument for the position to place the closing order against.
     *
     * @param instrumentId The instrument id for the order.
     */
    public void setInstrumentId(final long instrumentId)
    {
        this.instrumentId = instrumentId;
    }

    /**
     * Set the quantity of the close specification to the specified value.
     * This allows you to place multiple closing orders with the same parameters.
     *
     * @param quantity New quantity for the close position specification.
     */
    public void setQuantity(final FixedPointNumber quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Set the user correlation ID for this closing order.  This must be unique for each new closing order that is placed.
     *
     * @param instructionId the user-specified correlation ID
     */
    public void setInstructionId(final String instructionId)
    {
        this.instructionId = instructionId;
    }

    /**
     * Setting an originalInstructionId on the specification means this closing order will close some
     * or all of an existing open order with the given originalInstructionId.  Instrument ID is mandatory
     * if originalInstructionId is present.
     *
     * @param originalInstructionId the instruction ID of the original order to close
     */
    public void setOriginalInstructionId(final String originalInstructionId)
    {
        this.originalInstructionId = originalInstructionId;
    }

    /**
     * The close position uri.
     *
     * @return The close position uri.
     */
    @Override
    public String getUri()
    {
        if (isSpecificationForClosingAnIndividualOrder())
        {
            return "/secure/trade/closeOutOrder";
        }
        else
        {
            return "/secure/trade/closeOutInstrumentPosition";
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
        validate();
        writer.startElement("req").startElement("body").
            value("instructionId", instructionId).
            valueOrNone("instrumentId", instrumentId, 0).
            valueOrNone("originalInstructionId", originalInstructionId).
            value("quantity", quantity).
            endElement("body").endElement("req");
    }

    /**
     * A readable String representation of the ClosingOrderSpecification.
     * @return ClosingOrderSpecification as a String
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("ClosingOrderSpecification")
            .append("[instrumentId=").append(instrumentId)
            .append(", instructionId=").append(instructionId)
            .append(", originalInstructionId=").append(originalInstructionId)
            .append(", quantity=").append(quantity)
            .append(']').toString();
    }

    private boolean isSpecificationForClosingAnIndividualOrder()
    {
        return originalInstructionId != NO_INSTRUCTION_ID;
    }

    private void validate()
    {
        if (quantity == null)
        {
            throw new IllegalArgumentException("Quantity required");
        }
    }
}
