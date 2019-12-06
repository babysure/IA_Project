package com.lmax.api.internal.events;

import java.util.HashMap;
import java.util.Map;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.TimeInForce;
import com.lmax.api.order.Order;
import com.lmax.api.order.OrderType;

public class OrderEventBuilder
{
    private String instructionId;
    private String originalInstructionId;
    private String orderId;
    private long instrumentId;
    private long accountId;
    private FixedPointNumber quantity;
    private FixedPointNumber filledQuantity;
    private FixedPointNumber price;
    private OrderType orderType;
    private FixedPointNumber cancelledQuantity;
    private FixedPointNumber stopReferencePrice;
    private FixedPointNumber stopProfitOffset;
    private FixedPointNumber stopLossOffset;
    private boolean trailingStopLoss = false;
    private FixedPointNumber commission;
    private final Map<String, OrderType> orderTypes = new HashMap<>();
    private final Map<String, TimeInForce> timeInForceTypes = new HashMap<>();
    private TimeInForce timeInForce = TimeInForce.UNKNOWN;

    public OrderEventBuilder()
    {
        orderTypes.put("STOP_COMPOUND_PRICE_LIMIT", OrderType.LIMIT);
        orderTypes.put("PRICE_LIMIT", OrderType.LIMIT);
        orderTypes.put("STOP_COMPOUND_MARKET", OrderType.MARKET);
        orderTypes.put("MARKET_ORDER", OrderType.MARKET);
        
        timeInForceTypes.put("GoodTilCancelled", TimeInForce.GOOD_TIL_CANCELLED);
        timeInForceTypes.put("GoodForDay", TimeInForce.GOOD_FOR_DAY);
        timeInForceTypes.put("ImmediateOrCancel", TimeInForce.IMMEDIATE_OR_CANCEL);
        timeInForceTypes.put("FillOrKill", TimeInForce.FILL_OR_KILL);
    }

    public Order newInstance()
    {
        return new OrderImpl(originalInstructionId, instructionId, orderId, instrumentId, accountId, orderType, timeInForce, quantity,
                             filledQuantity, cancelledQuantity, price, stopReferencePrice, stopLossOffset, trailingStopLoss, stopProfitOffset, commission);
    }

    public OrderEventBuilder instructionId(final String anInstructionId)
    {
        instructionId = anInstructionId;
        return this;
    }

    public OrderEventBuilder originalInstructionId(final String anOriginalInstructionId)
    {
        originalInstructionId = anOriginalInstructionId;
        return this;
    }

    public OrderEventBuilder orderId(final String anOrderId)
    {
        orderId = anOrderId;
        return this;
    }

    public OrderEventBuilder instrumentId(final long anInstrumentId)
    {
        this.instrumentId = anInstrumentId;
        return this;
    }

    public OrderEventBuilder accountId(final long anAccountId)
    {
        this.accountId = anAccountId;
        return this;
    }

    public OrderEventBuilder quantity(final FixedPointNumber aQuantity)
    {
        quantity = aQuantity;
        return this;
    }

    public OrderEventBuilder filledQuantity(final FixedPointNumber aFilledQuantity)
    {
        this.filledQuantity = aFilledQuantity;
        return this;
    }

    public OrderEventBuilder price(final FixedPointNumber aPrice)
    {
        this.price = aPrice;
        return this;
    }

    public OrderEventBuilder orderType(final OrderType anOrderType)
    {
        this.orderType = anOrderType;
        return this;
    }

    public OrderEventBuilder orderType(final String value)
    {
        final OrderType anOrderType = orderTypes.get(value);
        if(anOrderType != null)
        {
            this.orderType(anOrderType);
        }
        else
        {
            try
            {
                this.orderType(OrderType.valueOf(value));
            }
            catch (IllegalArgumentException e)
            {
                this.orderType(OrderType.UNKNOWN);
            }
        }
        return this;
    }

    public OrderEventBuilder cancelledQuantity(final FixedPointNumber aCancelledQuantity)
    {
        this.cancelledQuantity = aCancelledQuantity;
        return this;
    }

    public OrderEventBuilder stopReferencePrice(final FixedPointNumber aStopReferencePrice)
    {
        stopReferencePrice = aStopReferencePrice;
        return this;
    }

    public OrderEventBuilder stopProfitOffset(final FixedPointNumber aStopProfitOffset)
    {
        stopProfitOffset = aStopProfitOffset;
        return this;
    }

    public OrderEventBuilder stopLossOffset(final FixedPointNumber aStopLossOffset)
    {
        stopLossOffset = aStopLossOffset;
        return this;
    }

    public OrderEventBuilder commission(final FixedPointNumber aCommission)
    {
        commission = aCommission;
        return this;
    }

    public OrderEventBuilder timeInForce(TimeInForce aTimeInForce)
    {
        this.timeInForce = aTimeInForce;
        return this;
    }

    public OrderEventBuilder trailingStopLoss(final boolean aTrailingStopLoss)
    {
        this.trailingStopLoss = aTrailingStopLoss;
        return this;
    }

    public void timeInForce(String value)
    {
        final TimeInForce aTimeInForce = timeInForceTypes.get(value);
        if(aTimeInForce != null)
        {
            this.timeInForce(aTimeInForce);
        }
        else
        {
            this.timeInForce(TimeInForce.UNKNOWN);
        }

    }
}
