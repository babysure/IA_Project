package com.lmax.api.internal.events;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.orderbook.PricePoint;

public class PricePointImpl implements PricePoint
{
    private final FixedPointNumber price;
    private final FixedPointNumber quantity;

    public PricePointImpl(final FixedPointNumber quantity, final FixedPointNumber price)
    {
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public FixedPointNumber getPrice()
    {
        return price;
    }

    @Override
    public FixedPointNumber getQuantity()
    {
        return quantity;
    }

    @Override
    public String toString()
    {
        return "PricePoint{" +
               "price=" + price +
               ", quantity=" + quantity +
               '}';
    }
}
