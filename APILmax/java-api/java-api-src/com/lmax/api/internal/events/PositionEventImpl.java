package com.lmax.api.internal.events;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.position.PositionEvent;

public class PositionEventImpl implements PositionEvent
{
    private final long accountId;
    private final long instrumentId;
    private final FixedPointNumber valuation;
    private final FixedPointNumber shortUnfilledCost;
    private final FixedPointNumber longUnfilledCost;
    private final FixedPointNumber openQuantity;
    private final FixedPointNumber cumulativeCost;
    private final FixedPointNumber openCost;

    public PositionEventImpl(final long accountId, final long instrumentId, final FixedPointNumber valuation, final FixedPointNumber shortUnfilledCost, final FixedPointNumber longUnfilledCost,
                             final FixedPointNumber openQuantity,
                             final FixedPointNumber cumulativeCost, final FixedPointNumber openCost)
    {
        this.accountId = accountId;
        this.instrumentId = instrumentId;
        this.valuation = valuation;
        this.shortUnfilledCost = shortUnfilledCost;
        this.longUnfilledCost = longUnfilledCost;
        this.openQuantity = openQuantity;
        this.cumulativeCost = cumulativeCost;
        this.openCost = openCost;
    }

    public long getAccountId()
    {
        return accountId;
    }

    public long getInstrumentId()
    {
        return instrumentId;
    }

    public FixedPointNumber getValuation()
    {
        return valuation;
    }

    public FixedPointNumber getShortUnfilledCost()
    {
        return shortUnfilledCost;
    }

    public FixedPointNumber getLongUnfilledCost()
    {
        return longUnfilledCost;
    }

    public FixedPointNumber getOpenQuantity()
    {
        return openQuantity;
    }

    public FixedPointNumber getCumulativeCost()
    {
        return cumulativeCost;
    }

    public FixedPointNumber getOpenCost()
    {
        return openCost;
    }

    public String toString()
    {
        return "PositionEventImpl{" +
               "accountId=" + accountId +
               ", instrumentId=" + instrumentId +
               ", valuation=" + valuation +
               ", shortUnfilledCost=" + shortUnfilledCost +
               ", longUnfilledCost=" + longUnfilledCost +
               ", openQuantity=" + openQuantity +
               ", cumulativeCost=" + cumulativeCost +
               ", openCost=" + openCost +
               '}';
    }
}
