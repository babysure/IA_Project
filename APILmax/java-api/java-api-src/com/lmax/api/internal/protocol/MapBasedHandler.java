package com.lmax.api.internal.protocol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lmax.api.FixedPointNumber;


import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class MapBasedHandler extends Handler
{
    private final Map<String, Handler> handlers = new HashMap<>();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>()
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        }
    };

    public MapBasedHandler(final String elementName)
    {
        super(elementName);
    }

    @Override
    public final void reset(final String element)
    {
        super.reset(element);
        final Handler handler = handlers.get(element);
        if (handler != null)
        {
            handler.reset(element);
        }
    }

    protected FixedPointNumber getFixedPointNumberValue(final String nodeName)
    {
        final String tagContent = getStringValue(nodeName);
        if (tagContent.length() == 0)
        {
            return null;
        }
        else
        {
            return FixedPointNumber.valueOf(tagContent);
        }
    }

    protected long getLongValue(final String nodeName)
    {
        final String tagContent = getStringValue(nodeName);
        if (tagContent.length() == 0)
        {
            return 0L;
        }
        else
        {
            return parseLong(tagContent);
        }
    }

    protected int getIntValue(final String key)
    {
        final String tagContent = getStringValue(key);
        if (tagContent.length() == 0)
        {
            return 0;
        }
        else
        {
            return parseInt(tagContent);
        }
    }


    protected boolean getBooleanValue(final String nodeName)
    {
        final String tagContent = getStringValue(nodeName);
        return tagContent.length() != 0 && Boolean.parseBoolean(tagContent);
    }

    protected String getStringValue(final String key)
    {
        return handlers.get(key).getContent();
    }

    protected String getOptionalStringValue(final String key)
    {
        final String content = handlers.get(key).getContent();

        return content.isEmpty() ? null : content;
    }

    protected Date getDate(final String key) throws ParseException
    {
        final String tagContent = getStringValue(key);
        if (tagContent.length() == 0)
        {
            return null;
        }
        else
        {
            return DATE_FORMATTER.get().parse(tagContent);
        }
    }


    @Override
    public Handler getHandler(final String qName)
    {
        final Handler handler = handlers.get(qName);

        if (handler != null)
        {
            return handler;
        }
        else
        {
            return this;
        }
    }

    protected void addHandler(final Handler handler)
    {
        handlers.put(handler.getElementName(), handler);
    }

    protected void addHandler(final String tagName)
    {
        addHandler(new Handler(tagName));
    }

    public void resetAll()
    {
        for (final Map.Entry<String, Handler> handlerEntry : handlers.entrySet())
        {
            handlerEntry.getValue().reset(handlerEntry.getKey());
        }
    }
}
