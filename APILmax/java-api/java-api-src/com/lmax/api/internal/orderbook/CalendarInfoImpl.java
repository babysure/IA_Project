package com.lmax.api.internal.orderbook;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.lmax.api.orderbook.CalendarInfo;
import com.lmax.api.orderbook.DayOfWeek;


public class CalendarInfoImpl implements CalendarInfo
{
    private final Date startTime;
    private final Date expiryTime;
    private final int open;
    private final int close;
    private final String timeZone;
    private final List<DayOfWeek> tradingDays;

    public CalendarInfoImpl(final Date startTime, final Date expiryTime, final int open, final int close, final String timeZone, final List<DayOfWeek> tradingDays)
    {
        this.startTime = startTime;
        this.expiryTime = expiryTime;
        this.open = open;
        this.close = close;
        this.timeZone = timeZone;
        this.tradingDays = tradingDays;
    }

    @Override
    public Date getStartTime()
    {
        return startTime;
    }

    @Override
    public Date getExpiryTime()
    {
        return expiryTime;
    }

    @Override
    public int getOpen()
    {
        return open;
    }

    @Override
    public int getClose()
    {
        return close;
    }

    @Override
    public String getTimeZone()
    {
        return timeZone;
    }

    @Override
    public List<DayOfWeek> getTradingDays()
    {
        return tradingDays;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final CalendarInfoImpl that = (CalendarInfoImpl)o;

        if (close != that.close)
        { return false; }
        if (open != that.open)
        { return false; }
        if (expiryTime != null ? !expiryTime.equals(that.expiryTime) : that.expiryTime != null)
        { return false; }
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
        { return false; }
        if (timeZone != null ? !timeZone.equals(that.timeZone) : that.timeZone != null)
        { return false; }
        if (!compareDaysOfWeek(tradingDays, that.tradingDays))
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (expiryTime != null ? expiryTime.hashCode() : 0);
        result = 31 * result + open;
        result = 31 * result + close;
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (tradingDays != null ? tradingDays.hashCode() : 0);
        return result;
    }

    private boolean compareDaysOfWeek(List<DayOfWeek> thisObj, List<DayOfWeek> other)
    {
        if (thisObj == other)
        { return true; }
        if (thisObj == null || other == null)
        { return false; }

        int thisBitMap = 0;
        int otherBitMap = 0;

        for (final DayOfWeek dayOfWeek : thisObj)
        {
            thisBitMap |= (1 << dayOfWeek.ordinal());
        }

        for (final DayOfWeek dayOfWeek : other)
        {
            otherBitMap |= (1 << dayOfWeek.ordinal());
        }

        return thisBitMap == otherBitMap;
    }

    @Override
    public String toString()
    {
        final StringBuilder buffer = new StringBuilder();

        buffer.append("StartTime: ").append(startTime).append(startTime).append(", ExpiryTime: ").append(expiryTime).append(", Open: ").
            append(open).append(", Close: ").append(close).append(", TimeZone: ").append(timeZone).append(", TradingDays: ").append(Arrays.toString(tradingDays.toArray()));

        return buffer.toString();
    }
}
