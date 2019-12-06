package com.lmax.api.internal.events;

import java.util.Collections;
import java.util.List;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.orderbook.OrderBookEvent;
import com.lmax.api.orderbook.PricePoint;

/**
 * API representation for events received.
 */
public class OrderBookEventImpl implements OrderBookEvent
{
    private final long instrumentId;
    private final FixedPointNumber valuationBidPrice;
    private final long timeStamp;
    private final FixedPointNumber valuationAskPrice;
    private final FixedPointNumber marketClosePrice;
    private final List<PricePoint> bidPrices;
    private final List<PricePoint> askPrices;
    private final FixedPointNumber lastTradedPrice;
    private final FixedPointNumber dailyHighestTradedPrice;
    private final FixedPointNumber dailyLowestTradedPrice;
    private final long marketClosePriceTimeStamp;

    public OrderBookEventImpl(final long instrumentId,
                              final FixedPointNumber valuationBidPrice,
                              final FixedPointNumber valuationAskPrice,
                              final List<PricePoint> bidPricePoints,
                              final List<PricePoint> askPrices,
                              final FixedPointNumber marketClosePrice,
                              final long marketClosePriceTimeStamp,
                              final FixedPointNumber lastTradedPrice,
                              final FixedPointNumber dailyHighestTradedPrice,
                              final FixedPointNumber dailyLowestTradedPrice,
                              final long timeStamp)
    {
        this.instrumentId = instrumentId;
        this.valuationAskPrice = valuationAskPrice;
        this.valuationBidPrice = valuationBidPrice;
        this.timeStamp = timeStamp;
        this.bidPrices = Collections.unmodifiableList(bidPricePoints);
        this.askPrices = Collections.unmodifiableList(askPrices);
        this.marketClosePrice = marketClosePrice;
        this.marketClosePriceTimeStamp = marketClosePriceTimeStamp;
        this.lastTradedPrice = lastTradedPrice;
        this.dailyHighestTradedPrice = dailyHighestTradedPrice;
        this.dailyLowestTradedPrice = dailyLowestTradedPrice;
    }

    @Override
    public long getInstrumentId()
    {
        return instrumentId;
    }

    @Override
    public FixedPointNumber getValuationBidPrice()
    {
        return valuationBidPrice;
    }

    @Override
    public FixedPointNumber getValuationAskPrice()
    {
        return valuationAskPrice;
    }

    @Override
    public List<PricePoint> getBidPrices()
    {
        return bidPrices;
    }

    @Override
    public List<PricePoint> getAskPrices()
    {
        return askPrices;
    }

    public FixedPointNumber getMarketClosePrice()
    {
        return marketClosePrice;
    }

    public long getMarketClosePriceTimeStamp()
    {
        return marketClosePriceTimeStamp;
    }

    @Override
    public FixedPointNumber getLastTradedPrice()
    {
        return lastTradedPrice;
    }

    @Override
    public FixedPointNumber getDailyHighestTradedPrice()
    {
        return dailyHighestTradedPrice;
    }

    @Override
    public FixedPointNumber getDailyLowestTradedPrice()
    {
        return dailyLowestTradedPrice;
    }

    @Override
    public long getTimeStamp()
    {
        return timeStamp;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("OrderBookEventImpl");
        sb.append("{instrumentId=").append(instrumentId);
        sb.append(", valuationBidPrice=").append(valuationBidPrice);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", valuationAskPrice=").append(valuationAskPrice);
        sb.append(", lastMarketClosePrice=").append(marketClosePrice);
        sb.append(", bidPrices=").append(bidPrices);
        sb.append(", askPrices=").append(askPrices);
        sb.append(", lastTradedPrice=").append(lastTradedPrice);
        sb.append(", dailyHighestTradedPrice=").append(dailyHighestTradedPrice);
        sb.append(", dailyLowestTradedPrice=").append(dailyLowestTradedPrice);
        sb.append(", marketClosePriceTimeStamp='").append(marketClosePriceTimeStamp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
