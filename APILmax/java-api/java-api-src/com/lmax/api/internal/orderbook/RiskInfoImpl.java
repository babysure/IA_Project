package com.lmax.api.internal.orderbook;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.orderbook.RiskInfo;

public class RiskInfoImpl implements RiskInfo
{
    private final FixedPointNumber marginRate;
    private final FixedPointNumber maximumPosition;

    public RiskInfoImpl(final FixedPointNumber marginRate, final FixedPointNumber maximumPosition)
    {
        this.marginRate = marginRate;
        this.maximumPosition = maximumPosition;
    }

    @Override
    public FixedPointNumber getMarginRate()
    {
        return marginRate;
    }

    @Override
    public FixedPointNumber getMaximumPosition()
    {
        return maximumPosition;
    }

    @Override
    public String toString()
    {
        return "RiskInfo{" +
               "marginRate=" + marginRate +
               ", maximumPosition=" + maximumPosition +
               '}';
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final RiskInfoImpl riskInfo = (RiskInfoImpl)o;

        if (marginRate != null ? !marginRate.equals(riskInfo.marginRate) : riskInfo.marginRate != null)
        { return false; }
        if (maximumPosition != null ? !maximumPosition.equals(riskInfo.maximumPosition) : riskInfo.maximumPosition != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = marginRate != null ? marginRate.hashCode() : 0;
        result = 31 * result + (maximumPosition != null ? maximumPosition.hashCode() : 0);
        return result;
    }
}
