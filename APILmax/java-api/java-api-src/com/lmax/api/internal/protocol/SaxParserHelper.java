package com.lmax.api.internal.protocol;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserHelper extends DefaultHandler
{
    private final Stack<Handler> handlers = new Stack<>();

    public SaxParserHelper(final Handler baseHandler)
    {
        handlers.push(baseHandler);
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes atts) throws SAXException
    {
        final Handler handler = handlers.elementAt(0);
        final String handlerElementName = handler.getElementName();
        if (handlerElementName != null && handlerElementName.equals(qName))
        {
            handlers.clear();
            handlers.push(handler);
        }
        Handler currentParentHandler = handlers.peek();
        Handler thisElementHandler = currentParentHandler.getHandler(qName);
        thisElementHandler.reset(qName);
        handlers.push(thisElementHandler);
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException
    {
        handlers.pop().endElement(qName);
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException
    {
        handlers.peek().characters(ch, start, length);
    }
}
