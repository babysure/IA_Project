package com.lmax.api.internal.protocol;

import java.util.Locale;

import com.lmax.api.orderbook.OrderBookStatus;
import com.lmax.api.orderbook.OrderBookStatusEvent;
import com.lmax.api.orderbook.OrderBookStatusEventListener;

import org.xml.sax.SAXException;

public class OrderBookStatusEventHandler extends MapBasedHandler
{
    private static final String ID = "id";
    private static final String STATUS = "status";
    private OrderBookStatusEventListener eventListener;

    public OrderBookStatusEventHandler()
    {
        super("orderBookStatus");
        addHandler(ID);
        addHandler(STATUS);
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (getElementName().equals(localName) && eventListener != null)
        {
            eventListener.notify(new OrderBookStatusEvent(getLongValue(ID), getStatus(getStringValue(STATUS))));
        }
    }

    private OrderBookStatus getStatus(final String value)
    {
        return OrderBookStatus.valueOf(value.toUpperCase(Locale.ENGLISH));
    }

    public void setListener(final OrderBookStatusEventListener anEventListener)
    {
        this.eventListener = anEventListener;
    }
}
