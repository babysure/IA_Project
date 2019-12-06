package com.lmax.api.orderbook;

import java.util.Date;
import java.util.List;

/**
 * Contains all of the information relating to dates and times during
 * which the instrument is tradeable.
 */
public interface CalendarInfo
{
    /**
     * Get the start time of the instrument, this is the time that the instrument
     * will first be available to trade.
     *
     * @return the start date.
     */
    Date getStartTime();

    /**
     * Get the Date the instrument will expire, mostly relevant for instruments
     * like futures that only exist for a limited time.
     *
     * @return the date the instrument will expiry.
     */
    Date getExpiryTime();

    /**
     * Get the time of day that the market will open (in time zone specified)
     * by this object.
     *
     * @return offset from midnight in minutes.
     */
    int getOpen();

    /**
     * Get the time of day that the market will close (in time zone specified)
     * by this object.
     *
     * @return offset from midnight in minutes.
     */
    int getClose();

    /**
     * Get the timezone in which this calendar operates.
     *
     * @return time zone.
     */
    String getTimeZone();

    /**
     * Get the days of week that the market is open.
     *
     * @return days of week the instrument is open.
     */
    List<DayOfWeek> getTradingDays();
}
