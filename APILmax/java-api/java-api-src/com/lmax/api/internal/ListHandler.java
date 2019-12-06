package com.lmax.api.internal;

import java.util.ArrayList;
import java.util.List;

import com.lmax.api.internal.protocol.Handler;

import org.xml.sax.SAXException;

public abstract class ListHandler<T> extends Handler
{
    private final List<T> contentList = new ArrayList<>();

    public ListHandler(final String elementName)
    {
        super(elementName);
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException
    {
        final String inputString = new StringBuilder().append(ch, start, length).toString();
        contentList.add(convert(inputString));
    }

    public List<T> getContentList()
    {
        final List<T> results = new ArrayList<>(contentList.size());
        results.addAll(contentList);
        return results;
    }

    public void reset()
    {
        contentList.clear();
    }

    public abstract T convert(final String inputToConvert);
}
