package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;
import com.lmax.api.internal.order.AbstractOrderSpecification;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Market Order Request.
 */
public class MarketOrderSpecification extends AbstractOrderSpecification
{

    /**
     * Construct a market order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the market order.  This can be "Fill or Kill" or
     *                              "Immediate or Cancel".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset Use null if you do not want to specify a stop.
     * @param trailingStop          Set to true if you want the stop on this order to trail.
     */
    public MarketOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber quantity, final TimeInForce timeInForce, final FixedPointNumber stopLossPriceOffset,
                                    final FixedPointNumber stopProfitPriceOffset, final boolean trailingStop)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, trailingStop);
        validateTimeInForce(timeInForce);
    }

    /**
     * Construct a market order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the market order.  This can be "Fill or Kill" or
     *                              "Immediate or Cancel".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset Use null if you do not want to specify a stop.
     */
    public MarketOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber quantity, final TimeInForce timeInForce, final FixedPointNumber stopLossPriceOffset,
                                    final FixedPointNumber stopProfitPriceOffset)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, false);
        validateTimeInForce(timeInForce);
    }

    /**
     * Construct a market order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the market order.  This can be "Fill or Kill" or
     *                              "Immediate or Cancel".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset Use null if you do not want to specify a stop.
     */
    public MarketOrderSpecification(final long instrumentId, final FixedPointNumber quantity, final TimeInForce timeInForce, final FixedPointNumber stopLossPriceOffset,
                                    final FixedPointNumber stopProfitPriceOffset)
    {
        this(instrumentId, null, quantity, timeInForce, stopLossPriceOffset, stopProfitPriceOffset);
    }

    /**
     * Construct a market order request with no stops.
     *
     * @param instrumentId The id of the instrument for the order book to place the order onto.
     * @param quantity     The quantity to trade.  A signed value, where the sign indicates the side of the
     *                     market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce  The time in force policy for the market order.  This can be "Fill or Kill" or
     *                     "Immediate or Cancel".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    public MarketOrderSpecification(final long instrumentId, final FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, quantity, timeInForce, null, null);

    }

    /**
     * Construct a market order request with no stops.
     *
     * @param instrumentId  The id of the instrument for the order book to place the order onto.
     * @param instructionId The user-defined correlation id
     * @param quantity      The quantity to trade.  A signed value, where the sign indicates the side of the
     *                      market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce   The time in force policy for the market order.  This can be "Fill or Kill" or
     *                      "Immediate or Cancel".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    public MarketOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, instructionId, quantity, timeInForce, null, null);
    }

    /**
     * The time in force policy for the market order. This can be "Fill or Kill" or "Immediate or Cancel".
     *
     * @param timeInForce An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    @Override
    public void setTimeInForce(final TimeInForce timeInForce)
    {
        validateTimeInForce(timeInForce);
        super.setTimeInForce(timeInForce);
    }

    @Override
    protected void writeOrderSpecificTagsTo(final StructuredWriter writer)
    {
    }

    private static void validateTimeInForce(final TimeInForce timeInForce)
    {
        if (timeInForce != TimeInForce.FILL_OR_KILL && timeInForce != TimeInForce.IMMEDIATE_OR_CANCEL)
        {
            throw new IllegalArgumentException("Market order only supports FILL_OR_KILL or IMMEDIATE_OR_CANCEL");
        }
    }

    /**
     * A readable String representation of the MarketOrderSpecification.
     * @return MarketOrderSpecification as a String
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("MarketOrderSpecification[")
            .append(super.toString())
            .append(']')
            .toString();
    }
}
