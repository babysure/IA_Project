package com.lmax.api.internal.protocol;

import java.util.ArrayList;
import java.util.List;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.events.OrderBookEventImpl;
import com.lmax.api.internal.events.PricePointImpl;
import com.lmax.api.orderbook.OrderBookEvent;
import com.lmax.api.orderbook.OrderBookEventListener;
import com.lmax.api.orderbook.PricePoint;

import org.xml.sax.SAXException;

public class OrderBookEventHandler extends Handler
{
    private static final char SEPARATOR = '|';

    private OrderBookEventListener orderBookEventListener;

    public OrderBookEventHandler()
    {
        super("ob2");
    }

    @Override
    public void endElement(final String qName) throws SAXException
    {
        if (qName != null && getElementName().equals(qName))
        {
            char[] content = getContent().toCharArray();

            int[] separators = new int[2];
            separators[1] = -1;

            //    InstrumentId '|' Timestamp '|' Bids '|' Asks '|' MarketClose '|' DailyHigh '|' DailyLow '|' ValuationBid '|' ValuationAsk '|' LastTraded
            findNextField(content, separators);
            long instrumentId = parseLong(content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            long timeStamp = parseOptionalTimestamp(content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            List<PricePoint> bids = new ArrayList<>(5);
            getPricePoints(bids, content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            List<PricePoint> asks = new ArrayList<>(5);
            getPricePoints(asks, content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            int marketCloseStartIndex = separators[0] + 1;
            int marketCloseEndIndex = separators[1];
            FixedPointNumber marketClosePrice = null;
            long marketClosePriceTimestamp = -1;
            if (marketCloseEndIndex != marketCloseStartIndex)
            {
                int separatorIndex = indexOf(content, ';', marketCloseStartIndex, marketCloseEndIndex);
                if (separatorIndex == -1)
                {
                    throw new IllegalArgumentException("Invalid market close");
                }
                marketClosePrice = FixedPointNumber.valueOf(content, marketCloseStartIndex, separatorIndex - marketCloseStartIndex);
                marketClosePriceTimestamp = parseOptionalTimestamp(content, separatorIndex + 1, marketCloseEndIndex);
            }

            findNextField(content, separators);
            FixedPointNumber dailyHighestTradedPrice = parsePrice(content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            FixedPointNumber dailyLowestTradedPrice = parsePrice(content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            FixedPointNumber valuationBidPrice = parsePrice(content, separators[0] + 1, separators[1]);

            findNextField(content, separators);
            FixedPointNumber valuationAskPrice = parsePrice(content, separators[0] + 1, separators[1]);

            FixedPointNumber lastTradedPrice = parsePrice(content, separators[1] + 1, content.length);

            orderBookEventListener.notify(new OrderBookEventImpl(instrumentId, valuationBidPrice, valuationAskPrice,
                                                                 bids, asks, marketClosePrice,
                                                                 marketClosePriceTimestamp, lastTradedPrice, dailyHighestTradedPrice,
                                                                 dailyLowestTradedPrice, timeStamp));
        }
    }

    public void setListener(final OrderBookEventListener anOrderBookEventListener)
    {
        this.orderBookEventListener = anOrderBookEventListener;
    }

    private void findNextField(char[] content, int[] separators)
    {
        separators[0] = separators[1];
        separators[1] = indexOf(content, SEPARATOR, separators[0] + 1);
        if (separators[1] == -1)
        {
            throw new IllegalArgumentException("Expected '" + SEPARATOR + "'");
        }
    }

    private int indexOf(char[] content, char ch, int start)
    {
        return indexOf(content, ch, start, content.length);
    }

    private int indexOf(char[] content, char ch, int start, int end)
    {
        for (int i = start; i < end; i++)
        {
            if (content[i] == ch)
            {
                return i;
            }
        }
        return -1;
    }

    private void getPricePoints(List<PricePoint> pricePoints, final char[] content, int start, int end)
    {
        if (end == start)
        {
            return;
        }
        boolean expectingAt = true;
        FixedPointNumber quantity = null;
        int nextFieldStart = start;
        for (int i = start; i < end; i++)
        {
            char ch = content[i];
            if (expectingAt)
            {
                if (ch == '@')
                {
                    quantity = FixedPointNumber.valueOf(content, nextFieldStart, i - nextFieldStart);
                    expectingAt = false;
                    nextFieldStart = i + 1;
                }
            }
            else if (ch == ';')
            {
                FixedPointNumber price = FixedPointNumber.valueOf(content, nextFieldStart, i - nextFieldStart);
                pricePoints.add(new PricePointImpl(quantity, price));
                expectingAt = true;
                nextFieldStart = i + 1;
            }
        }
        if (expectingAt)
        {
            throw new IllegalArgumentException("incomplete price points");
        }

        FixedPointNumber price = FixedPointNumber.valueOf(content, nextFieldStart, end - nextFieldStart);
        pricePoints.add(new PricePointImpl(quantity, price));
    }

    private FixedPointNumber parsePrice(char[] content, int start, int end)
    {
        return end == start ? null : FixedPointNumber.valueOf(content, start, end - start);
    }

    private long parseLong(char[] content, int start, int end)
    {
        return Long.parseLong(new String(content, start, end - start));
    }

    private long parseOptionalTimestamp(char[] content, int start, int end)
    {
        return start == end ? OrderBookEvent.NO_TIMESTAMP : Long.parseLong(new String(content, start, end - start), 16);
    }
}
