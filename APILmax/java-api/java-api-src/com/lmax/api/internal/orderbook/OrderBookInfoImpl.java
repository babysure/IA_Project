package com.lmax.api.internal.orderbook;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.orderbook.OrderBookInfo;

/**
 * Holds information related to the behaviour of the order book.
 */
public class OrderBookInfoImpl implements OrderBookInfo
{
    private final FixedPointNumber priceIncrement;
    private final FixedPointNumber quantityIncrement;
    private final FixedPointNumber volatilityBandPercentage;

    public OrderBookInfoImpl(final FixedPointNumber priceIncrement, final FixedPointNumber quantityIncrement, final FixedPointNumber volatilityBandPercentage)
    {
        this.priceIncrement = priceIncrement;
        this.quantityIncrement = quantityIncrement;
        this.volatilityBandPercentage = volatilityBandPercentage;
    }

    @Override
    public FixedPointNumber getPriceIncrement()
    {
        return priceIncrement;
    }

    @Override
    public FixedPointNumber getQuantityIncrement()
    {
        return quantityIncrement;
    }
    
    @Override
    public FixedPointNumber getVolatilityBandPercentage()
    {
        return volatilityBandPercentage;
    }

    @Override
    public String toString()
    {
        return "OrderBookInfo{" +
               "priceIncrement=" + priceIncrement +
               ", quantityIncrement=" + quantityIncrement +
               ", volatilityBandPercentage=" + volatilityBandPercentage +
               '}';
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final OrderBookInfoImpl that = (OrderBookInfoImpl)o;

        if (priceIncrement != null ? !priceIncrement.equals(that.priceIncrement) : that.priceIncrement != null)
        { return false; }
        if (quantityIncrement != null ? !quantityIncrement.equals(that.quantityIncrement) : that.quantityIncrement != null)
        { return false; }
        if (volatilityBandPercentage != null ? !volatilityBandPercentage.equals(that.volatilityBandPercentage) : that.volatilityBandPercentage != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = priceIncrement != null ? priceIncrement.hashCode() : 0;
        result = 31 * result + (quantityIncrement != null ? quantityIncrement.hashCode() : 0);
        result = 31 * result + (volatilityBandPercentage != null ? volatilityBandPercentage.hashCode() : 0);
        return result;
    }
}
