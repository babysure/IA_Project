package com.lmax.api.internal.protocol;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.events.PricePointImpl;
import com.lmax.api.orderbook.PricePoint;

public class PricePointBuilder
{
    private FixedPointNumber price;
    private FixedPointNumber quantity;

    public PricePointBuilder setPrice(final FixedPointNumber aPrice)
    {
        this.price = aPrice;
        return this;
    }

    public PricePointBuilder setQuantity(final FixedPointNumber aQuantity)
    {
        this.quantity = aQuantity;
        return this;
    }

    public PricePoint newInstance()
    {
        return new PricePointImpl(quantity, price);
    }
}
