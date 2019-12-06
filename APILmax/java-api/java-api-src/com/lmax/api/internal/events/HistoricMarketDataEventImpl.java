package com.lmax.api.internal.events;

import java.net.URL;
import java.util.List;

import com.lmax.api.orderbook.HistoricMarketDataEvent;

public class HistoricMarketDataEventImpl implements HistoricMarketDataEvent
{
    private final String instructionId;
    private final List<URL> urls;

    public HistoricMarketDataEventImpl(final String instructionId, final List<URL> urls)
    {
        this.instructionId = instructionId;
        this.urls = urls;
    }

    @Override
    public String getInstructionId()
    {
        return instructionId;
    }

    @Override
    public List<URL> getUrls()
    {
        return urls;
    }

    @Override
    public String toString()
    {
        return "HistoricMarketDataEventImpl{" +
               "instructionId=" + instructionId +
               ", urls=" + urls +
               '}';
    }
}
