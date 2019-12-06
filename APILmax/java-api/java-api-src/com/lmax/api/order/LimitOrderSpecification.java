package com.lmax.api.order;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;
import com.lmax.api.internal.order.AbstractOrderSpecification;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Limit Order Request.
 */
public final class LimitOrderSpecification extends AbstractOrderSpecification
{
    private FixedPointNumber price;

    /**
     * Construct a limit order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param price                 The price order the limit order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the limit order.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param trailingStop          Set to true if you want the stop on this order to trail.
     */
    public LimitOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber price, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                   final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset, final boolean trailingStop)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, trailingStop);
        this.price = price;
    }

    /**
     * Construct a limit order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param instructionId         The user-defined correlation id
     * @param price                 The price order the limit order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the limit order.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     */
    public LimitOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber price, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                   final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset)
    {
        super(instrumentId, instructionId, timeInForce, quantity, stopLossPriceOffset, stopProfitPriceOffset, false);
        this.price = price;
    }

    /**
     * Construct a limit order request.
     *
     * @param instrumentId          The id of the instrument for the order book to place the order onto.
     * @param price                 The price order the limit order
     * @param quantity              The quantity to trade.  A signed value, where the sign indicates the side of the
     *                              market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce           The time in force policy for the limit order.
     * @param stopLossPriceOffset   This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     * @param stopProfitPriceOffset This is meant to be an OFFSET not a ABSOLUTE price. The system will cap the offset value
     *                              to keep it within bounds for the instrument. Use null if you do not want to specify a stop.
     */
    public LimitOrderSpecification(final long instrumentId, final FixedPointNumber price, final FixedPointNumber quantity, final TimeInForce timeInForce,
                                   final FixedPointNumber stopLossPriceOffset, final FixedPointNumber stopProfitPriceOffset)
    {
        this(instrumentId, null, price, quantity, timeInForce, stopLossPriceOffset, stopProfitPriceOffset);
    }


    /**
     * Construct a limit order request with no stops.
     *
     * @param instrumentId The id of the instrument for the order book to place the order onto.
     * @param price        The price order the limit order.
     * @param quantity     The quantity to trade.  A signed value, where the sign indicates the side of the
     *                     market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce  The time in force policy for the limit order.
     */
    public LimitOrderSpecification(long instrumentId, FixedPointNumber price, FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, null, price, quantity, timeInForce, null, null);

    }

    /**
     * Construct a limit order request with no stops.
     *
     * @param instrumentId  The id of the instrument for the order book to place the order onto.
     * @param instructionId The user-defined correlation id
     * @param price         The price order the limit order.
     * @param quantity      The quantity to trade.  A signed value, where the sign indicates the side of the
     *                      market that order is placed.  Positive implies a buy order, where as negative is a sell
     * @param timeInForce   The time in force policy for the limit order.
     */
    public LimitOrderSpecification(final long instrumentId, final String instructionId, final FixedPointNumber price, final FixedPointNumber quantity, final TimeInForce timeInForce)
    {
        this(instrumentId, instructionId, price, quantity, timeInForce, null, null);
    }


    /**
     * Set the price of the limit order specification to the specified value.
     * This allows you to place multiple orders with different prices using one order specification.
     *
     * @param price New price for the order specification.
     */
    public void setPrice(final FixedPointNumber price)
    {
        this.price = price;
    }

    @Override
    protected void writeOrderSpecificTagsTo(final StructuredWriter writer)
    {
        writer.value("price", price);
    }

    /**
     * A readable String representation of the LimitOrderSpecification.
     * @return LimitOrderSpecification as a String
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("LimitOrderSpecification[")
            .append(super.toString())
            .append(", price=").append(price)
            .append(']')
            .toString();
    }
}
