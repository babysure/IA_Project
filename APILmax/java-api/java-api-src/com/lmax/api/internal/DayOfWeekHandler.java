package com.lmax.api.internal;

import com.lmax.api.orderbook.DayOfWeek;

public class DayOfWeekHandler extends ListHandler<DayOfWeek>
{
    public DayOfWeekHandler(final String elementName)
    {
        super(elementName);
    }

    @Override
    public DayOfWeek convert(final String inputToConvert)
    {
        return DayOfWeek.valueOf(inputToConvert);
    }
}
