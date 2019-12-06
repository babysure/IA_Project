package com.lmax.api.internal.protocol;

import com.lmax.api.account.AccountStateEventListener;
import com.lmax.api.heartbeat.HeartbeatEventListener;
import com.lmax.api.order.ExecutionEventListener;
import com.lmax.api.order.OrderEventListener;
import com.lmax.api.orderbook.HistoricMarketDataEventListener;
import com.lmax.api.orderbook.OrderBookEventListener;
import com.lmax.api.orderbook.OrderBookStatusEventListener;
import com.lmax.api.position.PositionEventListener;
import com.lmax.api.reject.InstructionRejectedEventListener;

public class EventHandler extends Handler
{
    private final AccountStateEventHandler accountStateEventHandler;
    private final HeartbeatEventHandler heartbeatEventHandler;
    private final HistoricMarketDataEventHandler historicMarketDataEventHandler;
    private final InstructionRejectedEventHandler instructionRejectedEventHandler;
    private final OrderBookEventHandler orderBookEventHandler;
    private final OrderBookStatusEventHandler orderBookStatusEventHandler;
    private final OrderStateEventHandler orderStateEventHandler;
    private final PositionEventHandler positionEventHandler;

    public EventHandler(String urlBase)
    {
        this(new AccountStateEventHandler(),
             new HeartbeatEventHandler(),
             new HistoricMarketDataEventHandler(),
             new InstructionRejectedEventHandler(),
             new OrderBookEventHandler(),
             new OrderBookStatusEventHandler(),
             new OrderStateEventHandler(),
             new PositionEventHandler());
    }

    // @ExposedForTesting
    EventHandler(final AccountStateEventHandler accountStateEventHandler,
                 final HeartbeatEventHandler heartbeatEventHandler,
                 final HistoricMarketDataEventHandler historicMarketDataEventHandler,
                 final InstructionRejectedEventHandler instructionRejectedEventHandler,
                 final OrderBookEventHandler orderBookEventHandler,
                 final OrderBookStatusEventHandler orderBookStatusEventHandler,
                 final OrderStateEventHandler orderStateEventHandler,
                 final PositionEventHandler positionEventHandler)
    {
        super("events");
        this.accountStateEventHandler = accountStateEventHandler;
        this.heartbeatEventHandler = heartbeatEventHandler;
        this.historicMarketDataEventHandler = historicMarketDataEventHandler;
        this.instructionRejectedEventHandler = instructionRejectedEventHandler;
        this.orderBookEventHandler = orderBookEventHandler;
        this.orderBookStatusEventHandler = orderBookStatusEventHandler;
        this.orderStateEventHandler = orderStateEventHandler;
        this.positionEventHandler = positionEventHandler;
    }

    @Override
    public Handler getHandler(String qName)
    {
        Handler handler;

        if (qName.equals(accountStateEventHandler.getElementName()))
        {
            handler = accountStateEventHandler;
        }
        else if (qName.equals(heartbeatEventHandler.getElementName()))
        {
            handler = heartbeatEventHandler;
        }
        else if (qName.equals(historicMarketDataEventHandler.getElementName()))
        {
            handler = historicMarketDataEventHandler;
        }
        else if (qName.equals(instructionRejectedEventHandler.getElementName()))
        {
            handler = instructionRejectedEventHandler;
        }
        else if (qName.equals(orderBookEventHandler.getElementName()))
        {
            handler = orderBookEventHandler;
        }
        else if (qName.equals(orderBookStatusEventHandler.getElementName()))
        {
            handler = orderBookStatusEventHandler;
        }
        else if (qName.equals(orderStateEventHandler.getElementName()))
        {
            handler = orderStateEventHandler;
        }
        else if (qName.equals(positionEventHandler.getElementName()))
        {
            handler = positionEventHandler;
        }
        else
        {
            return this;
        }
        return handler;
    }

    public void registerEventListener(AccountStateEventListener accountStateEventListener)
    {
        accountStateEventHandler.setListener(accountStateEventListener);
    }

    public void registerEventListener(ExecutionEventListener executionListener)
    {
        orderStateEventHandler.setListener(executionListener);
    }

    public void registerEventListener(HeartbeatEventListener eventListener)
    {
        heartbeatEventHandler.setListener(eventListener);
    }

    public void registerEventListener(HistoricMarketDataEventListener eventListener)
    {
        historicMarketDataEventHandler.setListener(eventListener);
    }

    public void registerEventListener(InstructionRejectedEventListener eventListener)
    {
        instructionRejectedEventHandler.setListener(eventListener);
    }

    public void registerEventListener(OrderBookEventListener orderBookEventListener)
    {
        orderBookEventHandler.setListener(orderBookEventListener);
    }

    public void registerEventListener(OrderBookStatusEventListener eventListener)
    {
        orderBookStatusEventHandler.setListener(eventListener);
    }

    public void registerEventListener(OrderEventListener orderEventListener)
    {
        orderStateEventHandler.setListener(orderEventListener);
    }

    public void registerEventListener(PositionEventListener positionEventListener)
    {
        positionEventHandler.setListener(positionEventListener);
    }
}
