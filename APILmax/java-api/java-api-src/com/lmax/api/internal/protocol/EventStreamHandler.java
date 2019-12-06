package com.lmax.api.internal.protocol;


import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EventStreamHandler
{
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    private final SAXParserFactory saxParserFactory;
    private final DefaultHandler saxParserHelper;
    
    public EventStreamHandler(final DefaultHandler helper)
    {
        this(helper, SAXParserFactory.newInstance());
    }

    // @ExposedForTesting
    protected EventStreamHandler(final DefaultHandler helper, final SAXParserFactory saxParserFactory)
    {
        this.saxParserHelper = helper;
        this.saxParserFactory = saxParserFactory;
    }

    public void parseEventStream(InputStream inputStream) throws IOException, SAXException
    {
        SAXParser saxParser;
        try
        {
            saxParserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            saxParser = saxParserFactory.newSAXParser();

            final InputStream headWrapper = new SequenceInputStream(new ByteArrayInputStream("<o>".getBytes(UTF_8_CHARSET)), inputStream);
            final InputStream tailWrapper = new ByteArrayInputStream("</o>".getBytes(UTF_8_CHARSET));
            final SequenceInputStream wrappedInputStream = new SequenceInputStream(headWrapper, tailWrapper);

            saxParser.parse(new InputSource(wrappedInputStream), saxParserHelper);

            throw new EOFException("Event stream disconnected");
        }
        catch (ParserConfigurationException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
