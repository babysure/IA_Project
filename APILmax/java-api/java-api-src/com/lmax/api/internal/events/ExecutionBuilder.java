package com.lmax.api.internal.events;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.order.Execution;
import com.lmax.api.order.Order;

public class ExecutionBuilder
{
    private FixedPointNumber price;
    private FixedPointNumber quantity;
    private long executionId;
    private Order order;
    private FixedPointNumber cancelledQuantity;
    private String encodedExecutionId;

    public ExecutionBuilder executionId(final long value)
    {
        executionId = value;
        return this;
    }

    public ExecutionBuilder price(final FixedPointNumber value)
    {
        price = value;
        return this;
    }

    public ExecutionBuilder quantity(final FixedPointNumber value)
    {
        quantity = value;
        return this;
    }

    public ExecutionBuilder order(final Order value)
    {
        order = value;
        return this;
    }

    public ExecutionBuilder encodedExecutionId(final String value)
    {
        encodedExecutionId = value;
        return this;
    }

    public ExecutionBuilder cancelledQuantity(final FixedPointNumber value)
    {
        this.cancelledQuantity = value;
        return this;
    }

    public Execution newInstance()
    {
        FixedPointNumber qty = getNotNullValue(quantity);
        FixedPointNumber cancelledQty = getNotNullValue(cancelledQuantity);
        return new ExecutionImpl(executionId, price, qty, order, cancelledQty, encodedExecutionId);
    }

    private FixedPointNumber getNotNullValue(final FixedPointNumber field)
    {
        FixedPointNumber qty;
        if (field != null)
        {
            qty = field;
        }
        else
        {
            qty = FixedPointNumber.ZERO;
        }
        return qty;
    }

    @Override
    public String toString()
    {
        return "ExecutionBuilder{" +
               "cancelledQuantity=" + cancelledQuantity +
               ", encodedExecutionId='" + encodedExecutionId + '\'' +
               ", executionId=" + executionId +
               ", order=" + order +
               ", price=" + price +
               ", quantity=" + quantity +
               '}';
    }
}
