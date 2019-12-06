package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;
import com.lmax.api.internal.order.AbstractOrderSpecification;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Stop Order Request.
 */
public final class StopOrderSpecification extends AbstractOrderSpecification
{
    private FixedPointNumber stopPrice;

    /**
     * Construct a stop order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param stopPrice             The price which will trigger the order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the stop order. This can be "Good For Day" or
     *                              "Good Till Cancelled".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param trailingStop          Set to true if you want the stop on this order to trail.
     */
    public StopOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber stopPrice, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                  final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset, final boolean trailingStop)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, trailingStop);
        this.stopPrice = stopPrice;
        validateTimeInForce(timeInForce);
    }

    /**
     * Construct a stop order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param stopPrice             The price which will trigger the order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the stop order. This can be "Good For Day" or
     *                              "Good Till Cancelled".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     */
    public StopOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber stopPrice, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                  final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, false);
        this.stopPrice = stopPrice;
        validateTimeInForce(timeInForce);
    }

    /**
     * Construct a stop order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param stopPrice             The price which will trigger the order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the stop order. This can be "Good For Day" or
     *                              "Good Till Cancelled".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     */
    public StopOrderSpecification(final long instrumentId, final FixedPointNumber stopPrice, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                  final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset)
    {
        this(instrumentId, null, stopPrice, quantity, timeInForce, stopLossPriceOffset, stopProfitPriceOffset);
    }


    /**
     * Construct a stop order request with no stop loss offsets.
     *
     * @param instrumentId The id of the instrument for the order book to place the order onto.
     * @param stopPrice    The price which will trigger the order
     * @param quantity     The quantity to trade.  A signed value, where the sign indicates the side of the
     *                     market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce  The time in force policy for the stop order. This can be "Good For Day" or
     *                     "Good Till Cancelled".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    public StopOrderSpecification(long instrumentId, FixedPointNumber stopPrice, FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, null, stopPrice, quantity, timeInForce, null, null);

    }

    /**
     * Construct a stop order request with no stop offsets.
     *
     * @param instrumentId  The id of the instrument for the order book to place the order onto.
     * @param instructionId The user-defined correlation id
     * @param stopPrice     The price which will trigger the order
     * @param quantity      The quantity to trade.  A signed value, where the sign indicates the side of the
     *                      market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce   The time in force policy for the stop order. This can be "Good For Day" or
     *                      "Good Till Cancelled".  An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    public StopOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber stopPrice, final FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, instructionId, stopPrice, quantity, timeInForce, null, null);
    }


    /**
     * Set the stop price of the Stop order specification to the specified value.
     * This allows you to place multiple orders with different prices using one order specification.
     *
     * @param stopPrice New price for the order specification.
     */
    public void setStopPrice(final FixedPointNumber stopPrice)
    {
        this.stopPrice = stopPrice;
    }

    @Override
    protected void writeOrderSpecificTagsTo(final StructuredWriter writer)
    {
        writer.value("stopPrice", stopPrice);
    }

    /**
     * The time in force policy for the stop order. This can be "Good for Day" or "Good till Cancelled".
     *
     * @param timeInForce An IllegalArgumentException will be thrown if an invalid time in force policy is used.
     */
    @Override
    public void setTimeInForce(final TimeInForce timeInForce)
    {
        validateTimeInForce(timeInForce);
        super.setTimeInForce(timeInForce);
    }

    /**
     * A readable String representation of the StopOrderSpecification.
     *
     * @return StopOrderSpecification as a String
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
                .append("StopOrderSpecification[")
                .append(super.toString())
                .append(", stopPrice=").append(stopPrice)
                .append(']')
                .toString();
    }

    private static void validateTimeInForce(final TimeInForce timeInForce)
    {
        if (timeInForce != TimeInForce.GOOD_FOR_DAY && timeInForce != TimeInForce.GOOD_TIL_CANCELLED)
        {
            throw new IllegalArgumentException("Stop order only supports GOOD_FOR_DAY or GOOD_TIL_CANCELLED");
        }
    }
}
