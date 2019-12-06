package com.lmax.api.orderbook;

import java.util.List;

import com.lmax.api.FixedPointNumber;

/**
 * Represents the current market depth of the Order Book.
 */
public interface OrderBookEvent
{
    /**
     * Value returned to signify no timestamp.
     *
     */
    long NO_TIMESTAMP = -1;

    /**
     * The instrument id this event applies to.
     *
     * @return instrument id
     */
    long getInstrumentId();

    /**
     * The valuation bid price.
     *
     * @return valuation bid price
     */
    FixedPointNumber getValuationBidPrice();

    /**
     * The valuation ask price.
     *
     * @return valuation ask price
     */
    FixedPointNumber getValuationAskPrice();

    /**
     * The bid price points for this instrument.
     * Ordered from best (highest) to worst (lowest) price.
     *
     * THIS LIST IS VALID ONLY AT THE TIME OF THE EVENT  NOTIFY CALL.
     * IF YOU WANT TO STORE IT FOR LATER, COPY THE EVENT.
     *
     * @return bid price points
     */
    List<PricePoint> getBidPrices();


    /**
     * The ask price points for this instrument.
     * Ordered from best (lowest) to worst (highest) price.
     *
     * THIS LIST IS VALID ONLY AT THE TIME OF THE EVENT  NOTIFY CALL.
     * IF YOU WANT TO STORE IT FOR LATER, COPY THE EVENT.
     *
     * @return ask price points
     */
    List<PricePoint> getAskPrices();

    /**
     * The price at the time of the last market close.
     *
     * @return price or null if no market close price for instrument
     */
    FixedPointNumber getMarketClosePrice();

    /**
     * The timestamp of the last market close price.
     *
     * @see #NO_TIMESTAMP
     * @return timestamp or NO_TIMESTAMP if no market close price for instrument
     */
    long getMarketClosePriceTimeStamp();

    /**
     * The last traded price.
     *
     * @return last traded price
     */
    FixedPointNumber getLastTradedPrice();

    /**
     * The highest traded price in the latest trading session.
     *
     * @return highest traded price
     */
    FixedPointNumber getDailyHighestTradedPrice();

    /**
     * The lowest traded price in the latest trading session.
     *
     * @return lowest traded price
     */
    FixedPointNumber getDailyLowestTradedPrice();

    /**
     * The time the prices were published.
     *
     * @return A milliseconds value representing the number of milliseconds that have passed since January 1, 1970 00:00:00.000 UTC
     */
    long getTimeStamp();
}
