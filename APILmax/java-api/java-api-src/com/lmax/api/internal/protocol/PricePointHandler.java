package com.lmax.api.internal.protocol;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.orderbook.PricePoint;

import org.xml.sax.SAXException;

public class PricePointHandler extends Handler
{
    private PricePointBuilder pricePointBuilder;
    private final Handler priceElementHandler = new Handler()
    {
        @Override
        public void endElement(final String localName) throws SAXException
        {
            pricePointBuilder.setPrice(FixedPointNumber.valueOf(getContent()));
        }
    };
    private final Handler quantityElementHandler = new Handler()
    {
        @Override
        public void endElement(final String localName) throws SAXException
        {
            pricePointBuilder.setQuantity(FixedPointNumber.valueOf(getContent()));
        }
    };

    public PricePointHandler()
    {
        super("pricePoint");
    }


    public PricePoint getPricePoint()
    {
        return pricePointBuilder.newInstance();
    }

    @Override
    public Handler getHandler(final String qName)
    {
        if ("price".equals(qName))
        {
            return priceElementHandler;

        }
        else if ("quantity".equals(qName))
        {
            return quantityElementHandler;
        }
        else
        {
            return this;
        }

    }

    @Override
    public void reset(final String element)
    {
        pricePointBuilder = new PricePointBuilder();
    }

}
