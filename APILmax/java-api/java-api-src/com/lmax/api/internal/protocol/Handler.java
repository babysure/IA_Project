package com.lmax.api.internal.protocol;

import org.xml.sax.SAXException;

public class Handler
{
    protected static final String OK = "OK";
    protected static final String BODY = "body";
    protected static final String MESSAGE = "message";
    public static final String STATUS = "status";

    private final String elementName;
    private final StringBuilder contentBuilder = new StringBuilder();
    
    public Handler()
    {
        elementName = null;
    }

    public Handler(final String elementName)
    {
        this.elementName = elementName;
    }

    public Handler getHandler(final String qName)
    {
        return this;
    }

    public String getContent()
    {
        return contentBuilder.toString();
    }

    public void characters(final char[] ch, final int start, final int length) throws SAXException
    {
        contentBuilder.append(ch, start, length);
    }

    public void reset(final String element)
    {
        contentBuilder.setLength(0);
    }

    public void endElement(final String localName) throws SAXException
    {
    }
    
    public String getElementName()
    {
        return elementName;
    }
}
