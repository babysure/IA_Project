package com.lmax.api.internal.xml;

import java.io.IOException;
import java.io.Writer;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class LoggingSaxParserHelper extends DefaultHandler
{
    private static final int EVENT_ROOT_TAG_DEPTH = 1;
    private final Writer writer;
    private int position = 0;
    private final DefaultHandler handler;

    public LoggingSaxParserHelper(DefaultHandler handler, Writer writer)
    {
        this.handler = handler;
        this.writer = writer;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        try
        {
            if (position == EVENT_ROOT_TAG_DEPTH)
            {
                writer.write("Start: ");
                writer.write(String.valueOf(System.currentTimeMillis()));
                writer.write("\nEvent: ");
            }
            
            writer.append('<').append(qName).append('>');
            position++;
        }
        catch (IOException e)
        {
            // No-op
        }
        finally
        {
            handler.startElement(uri, localName, qName, attributes);
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        try
        {
            writer.append("</").append(qName).append('>');
            position--;

            if (position == EVENT_ROOT_TAG_DEPTH)
            {
                writer.write("\nEnd: ");
                writer.write(String.valueOf(System.currentTimeMillis()));
                writer.write("\n");
                writer.flush();
            }
        }
        catch (IOException e)
        {
            // No-op
        }
        finally
        {            
            handler.endElement(uri, localName, qName);
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        try
        {
            writer.write(ch, start, length);
        }
        catch (IOException e)
        {
            // No-op
        }
        finally
        {            
            handler.characters(ch, start, length);
        }
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException
    {
        return handler.resolveEntity(publicId, systemId);
    }

    public void notationDecl(String name, String publicId, String systemId) throws SAXException
    {
        handler.notationDecl(name, publicId, systemId);
    }

    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException
    {
        handler.unparsedEntityDecl(name, publicId, systemId, notationName);
    }

    public void setDocumentLocator(Locator locator)
    {
        handler.setDocumentLocator(locator);
    }

    public void startDocument() throws SAXException
    {
        handler.startDocument();
    }

    public void endDocument() throws SAXException
    {
        handler.endDocument();
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        handler.startPrefixMapping(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException
    {
        handler.endPrefixMapping(prefix);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
    {
        handler.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
        handler.processingInstruction(target, data);
    }

    public void skippedEntity(String name) throws SAXException
    {
        handler.skippedEntity(name);
    }

    public void warning(SAXParseException e) throws SAXException
    {
        handler.warning(e);
    }

    public void error(SAXParseException e) throws SAXException
    {
        handler.error(e);
    }

    public void fatalError(SAXParseException e) throws SAXException
    {
        handler.fatalError(e);
    }
}
