package com.lmax.api.internal.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Deque;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <p>
 * High performance parser that reads a XML document from a stream firing SAX events.
 * </p>
 * <p>
 * The parser only supports elements and will fail if the documents has other nodes including attributes or entities.
 * </p>
 * @author Martin Thompson
 */
public final class ElementOnlyXmlReader
        implements XMLReader
{
    private ContentHandler contentHandler;

    private final CharacterBuffer contentBuffer = new CharacterBuffer(64);
    private final CharacterBuffer tagNameBuffer = new CharacterBuffer(64);

    private static final Attributes EMPTY_ATTR = new AttributesImpl();
    private static final char[] EMPTY_ARRAY = new char[0];

    private static final String[][] XML_ENTITIES =
            {
                    {"lt", "<"},
                    {"gt", ">"},
                    {"amp", "&"},
                    {"apos", "'"},
                    {"quot", "\""},
            };

    public boolean getFeature(final String name)
            throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support features");
    }

    public void setFeature(final String name, final boolean value)
            throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support features");
    }

    public Object getProperty(final String name)
            throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support properties");
    }

    public void setProperty(final String name, final Object value)
            throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support properties");
    }

    public void setEntityResolver(final EntityResolver resolver)
    {
    }

    public EntityResolver getEntityResolver()
    {
        return null;
    }

    public void setDTDHandler(final DTDHandler handler)
    {
    }

    public DTDHandler getDTDHandler()
    {
        return null;
    }

    public void setContentHandler(final ContentHandler handler)
    {
        contentHandler = handler;
    }

    public ContentHandler getContentHandler()
    {
        return contentHandler;
    }

    public void setErrorHandler(final ErrorHandler handler)
    {
    }

    public ErrorHandler getErrorHandler()
    {
        return null;
    }

    public void parse(final InputSource input)
            throws IOException, SAXException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support InputSource");
    }

    public void parse(final String systemId)
            throws IOException, SAXException
    {
        throw new SAXNotSupportedException("ElementOnlyXmlReader does not support systemId");
    }

    public void parse(final Reader reader)
            throws IOException, SAXException
    {
        contentHandler.startDocument();

        Deque<String> tagStack = new LinkedList<>();

        while (true)
        {
            int i = reader.read();
            if (-1 == i)
            {
                if (tagStack.size() > 0)
                {
                    throw new XmlParseException("End tag not found for name=" + tagStack.peekFirst());
                }

                contentHandler.endDocument();
                return;
            }

            if (Character.isWhitespace(i) && tagStack.size() == 0)
            {
                continue;
            }

            char ch = (char)i;

            if ('<' == ch)
            {
                final int position = contentBuffer.getPosition();
                if (position > 0)
                {
                    contentHandler.characters(contentBuffer.getBuffer(), 0, position);
                    contentBuffer.reset();
                }

                handleTag(reader, tagStack);
            }
            else
            {
                if ('>' == ch)
                {
                    throw new XmlParseException("Invalid character found in tag content, name=" + tagStack.peekFirst() + " char=" + ch);
                }

                if ('&' == ch)
                {
                    ch = handleEntity(reader);
                }

                final int position = contentBuffer.getPosition();
                if (position == contentBuffer.size())
                {
                    contentHandler.characters(contentBuffer.getBuffer(), 0, position);
                    contentBuffer.reset();
                }

                contentBuffer.append(ch);
            }
        }
    }

    private void handleTag(final Reader reader, final Deque<String> tagStack)
            throws IOException, SAXException
    {
        char ch = (char)reader.read();

        if ('?' == ch)
        {
            handleDeclarationAndProcessingInstructions(reader);
            return;
        }

        if ('!' == ch)
        {
            handleComment(reader);
            return;
        }

        handleNameTag(reader, tagStack, ch);
    }

    private void handleNameTag(final Reader reader, final Deque<String> tagStack, final char ch)
            throws IOException, SAXException
    {
        tagNameBuffer.reset();

        if ('/' == ch)
        {
            handleCloseNameTag(reader, tagStack);
            return;
        }

        if (!Character.isWhitespace(ch))
        {
            tagNameBuffer.append(ch);
        }

        char ch2 = readTagName(reader);
        if ('/' == ch2)
        {
            handleEmptyNameTag(reader);
            return;
        }

        String tagName = tagNameBuffer.toString();
        tagStack.addFirst(tagName);
        contentHandler.startElement("", tagName, tagName, EMPTY_ATTR);
    }

    private void handleDeclarationAndProcessingInstructions(final Reader reader)
            throws IOException
    {
        while (true)
        {
            char ch = (char)reader.read();
            if ('?' == ch)
            {
                ch = (char)reader.read();
                if ('>' == ch)
                {
                    return;
                }

                throw new XmlParseException("Invalid end of XML declaration encountered '" + ch + "' found after ?");
            }
        }
    }

    private void handleComment(final Reader reader)
            throws IOException
    {
        readCommentStart(reader);
        readCommentStart(reader);

        char ch;
        char prev1 = 0;
        char prev2 = 0;
        while (true)
        {
            ch = (char)reader.read();
            if ('>' == ch)
            {
                if ('-' == prev1 && '-' == prev2)
                {
                    break;
                }
            }

            prev1 = prev2;
            prev2 = ch;
        }
    }

    private void readCommentStart(Reader reader)
            throws IOException
    {
        char ch = (char)reader.read();
        if ('-' != ch)
        {
            throw new XmlParseException("Invalid character found in beginning of XML comment: '" + ch + "'");
        }
    }

    private void handleEmptyNameTag(final Reader reader)
            throws IOException, SAXException
    {
        final char ch;
        ch = (char)reader.read();
        if ('>' != ch)
        {
            throw new XmlParseException("Invalid character found in empty tag, name=" + tagNameBuffer + " char=" + ch);
        }

        String tagName = tagNameBuffer.toString();
        contentHandler.startElement("", tagName, tagName, EMPTY_ATTR);
        contentHandler.characters(EMPTY_ARRAY, 0, 0);
        contentHandler.endElement("", tagName, tagName);
    }

    private void handleCloseNameTag(final Reader reader, final Deque<String> tagStack)
            throws IOException, SAXException
    {
        final char ch = readTagName(reader);
        if ('>' != ch)
        {
            throw new XmlParseException("Invalid character found in closing tag, name=" + tagNameBuffer + " char=" + ch);
        }

        String tagName = tagNameBuffer.toString();
        String currentTagName = tagStack.removeFirst();
        if (!tagName.equals(currentTagName))
        {
            throw new XmlParseException("Invalid closing tag, name=" + tagNameBuffer + " missing close for name=" + currentTagName);
        }

        contentHandler.endElement("", tagName, tagName);
    }

    private char readTagName(final Reader reader)
            throws IOException
    {
        while (true)
        {
            final int i = reader.read();
            if (-1 == i)
            {
                throw new XmlParseException("Unexpected end of tag, name= " + tagNameBuffer);
            }

            final char ch = (char)i;
            if ('/' == ch || '>' == ch)
            {
                return ch;
            }

            if (Character.isWhitespace(ch))
            {
                continue;
            }

            if (!isValidNameChar(ch))
            {
                throw new XmlParseException("Invalid character found in tag, name=" + tagNameBuffer + " char=" + ch);
            }

            try
            {
                tagNameBuffer.append(ch);
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                throw new XmlParseException("Tag, name=" + tagNameBuffer + ch + " greater than max size " + tagNameBuffer.getPosition(), ex);
            }
        }
    }

    private boolean isValidNameChar(final char ch)
    {
        return
                Character.isLetter(ch) ||
                Character.isDigit(ch) ||
                '-' == ch ||
                '_' == ch ||
                '.' == ch;
    }

    private char handleEntity(final Reader reader)
            throws IOException
    {
        CharacterBuffer cb = new CharacterBuffer(5);
        char ch = '\0';
        for (int i = 0; i < 5; i++)
        {
            ch = (char)reader.read();
            if (';' == ch)
            {
                break;
            }
            cb.append(ch);
        }

        if (';' == ch)
        {
            String entity = cb.toString();
            for (String[] entityRec : XML_ENTITIES)
            {
                if (entityRec[0].equals(entity))
                {
                    return entityRec[1].charAt(0);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Illegal entity '").append(cb.toString()).append('\'');
        sb.append(", valid entities =");
        for (final String[] entityRec : XML_ENTITIES)
        {
            sb.append(' ').append(entityRec[0]);
        }
        sb.append(", marked up with '&' and ';' for begin and end characters");

        throw new XmlParseException(sb.toString());
    }


    public static final class CharacterBuffer
    {
        private int position = 0;
        private final char[] buffer;

        public CharacterBuffer(final int size)
        {
            buffer = new char[size];
        }

        public int getPosition()
        {
            return position;
        }

        public char[] getBuffer()
        {
            return buffer;
        }

        public void append(final char ch)
        {
            buffer[position++] = ch;
        }

        public int size()
        {
            return buffer.length;
        }

        public void reset()
        {
            position = 0;
        }

        @Override
        public String toString()
        {
            if (position >= buffer.length)
            {
                position = buffer.length;
            }

            return new String(buffer, 0, position);
        }
    }
}
