package com.lmax.api.internal.events;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.order.Execution;
import com.lmax.api.order.Order;

public class ExecutionImpl implements Execution
{
    private final long executionId;
    private final FixedPointNumber price;
    private final FixedPointNumber quantity;
    private final Order order;
    private final FixedPointNumber cancelledQuantity;
    private final String encodedExecutionId;

    public ExecutionImpl(
            final long executionId,
            final FixedPointNumber price,
            final FixedPointNumber quantity,
            final Order order,
            final FixedPointNumber cancelledQuantity,
            final String encodedExecutionId)
    {
        this.executionId = executionId;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.cancelledQuantity = cancelledQuantity;
        this.encodedExecutionId = encodedExecutionId;
    }

    @Override
    public FixedPointNumber getCancelledQuantity()
    {
        return cancelledQuantity;
    }

    @Override
    public long getExecutionId()
    {
        return executionId;
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
    public Order getOrder()
    {
        return order;
    }

    @Override
    public String getEncodedExecutionId()
    {
        return encodedExecutionId;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("ExecutionImpl");
        sb.append("{executionId=").append(executionId);
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append(", order=").append(order);
        sb.append(", cancelledQuantity=").append(cancelledQuantity);
        sb.append('}');
        return sb.toString();
    }
}
