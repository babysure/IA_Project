package com.lmax.api.internal.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import com.lmax.api.FixedPointNumber;

public class XmlStructuredWriter implements StructuredWriter
{
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final int DEFAULT_SIZE = 4096;
    private static final String LEFT = "<";
    private static final String RIGHT = ">";
    private static final String LEFT_CLOSE = "</";
    private static final String RIGHT_CLOSE = "/>";

    private final byte[] defaultData = new byte[DEFAULT_SIZE];
    private final StringBuilder builder = new StringBuilder();
    
    private byte[] currentData = defaultData;
    private int length = 0;

    @Override
    public XmlStructuredWriter startElement(CharSequence name)
    {
        writeOpenTag(name);
        return this;
    }

    @Override
    public XmlStructuredWriter value(CharSequence name, CharSequence value)
    {
        if (null != value)
        {
            builder.setLength(0);
            buildValidXml(value, builder);
            writeTag(name, builder);
        }
        else
        {
            writeEmptyTag(name);
        }
        
        return this;
    }

    @Override
    public StructuredWriter valueUTF8(CharSequence name, CharSequence value)
    {
        if (null != value)
        {
            writeOpenTag(name);
            
            // XXX: Mike 2011/05/23
            // Java's charset API is stupid, why can't I count 
            // the expected number of byte's for a CharSequence
            // or encode directly using a CharSequence.
            builder.setLength(0);
            buildValidXml(value, builder);
            CharBuffer cb = CharBuffer.wrap(builder);
            ByteBuffer bb = ByteBuffer.wrap(currentData);
            bb.position(length);
            // This code is a bit ropey in that it does
            // not handle overflow, but we only use charset
            // encoding for usernames and passwords, so
            // it won't actually happen.
            final CharsetEncoder encoder = UTF_8.newEncoder();
            encoder.encode(cb, bb, true);
            encoder.flush(bb);
            
            length = bb.position();
            
            writeCloseTag(name);
        }
        else
        {
            writeEmptyTag(name);
        }
        
        return this;
    }

    @Override
    public StructuredWriter valueOrNone(final String name, final CharSequence value)
    {
        if(value != null)
        {
            builder.setLength(0);
            buildValidXml(value, builder);
            writeTag(name, builder);
        }
        return this;
    }

    @Override
    public StructuredWriter value(String name, long value)
    {
        builder.setLength(0);
        builder.append(value);
        writeTag(name, builder);
        return this;
    }

    @Override
    public StructuredWriter valueOrNone(String name, long value, long nullValue)
    {
        if (value != nullValue)
        {
            builder.setLength(0);
            builder.append(value);
            writeTag(name, builder);
        }
        
        return this;
    }
    
    @Override
    public StructuredWriter value(String name, FixedPointNumber value)
    {
        if (value != null)
        {
            builder.setLength(0);
            value.toStringBuilder(builder);
            writeTag(name, builder);
        }
        else
        {
            writeEmptyTag(name);
        }
        
        return this;
    }

    @Override
    public StructuredWriter valueOrNone(String name, FixedPointNumber value)
    {
        if (value != null)
        {
            builder.setLength(0);
            value.toStringBuilder(builder);
            writeTag(name, builder);
        }
        
        return this;
    }

    @Override
    public StructuredWriter value(String name, boolean value)
    {
        writeTag(name, String.valueOf(value));
        return this;
    }

    @Override
    public XmlStructuredWriter writeEmptyTag(CharSequence name)
    {
        writeCharSequence(LEFT);
        writeCharSequence(name);
        writeCharSequence(RIGHT_CLOSE);
        
        return this;
    }

    @Override
    public XmlStructuredWriter endElement(CharSequence name)
    {
        writeCloseTag(name);
        return this;
    }

    public void reset()
    {
        length = 0;
        currentData = defaultData;
    }

    private void writeCharSequence(CharSequence string)
    {
        int requiredLength = string.length() + length;
        
        while (requiredLength > currentData.length)
        {
            increaseBufferSize();
        }
        
        for (int i = 0; i < string.length(); i++)
        {
            currentData[length++] = (byte) string.charAt(i);
        }
    }

    private void increaseBufferSize()
    {
        byte[] oldData = currentData;
        currentData = new byte[oldData.length << 1];
        System.arraycopy(oldData, 0, currentData, 0, length);
    }

    private void writeTag(CharSequence name, CharSequence value)
    {
        writeOpenTag(name);
        writeCharSequence(value);
        writeCloseTag(name);
    }

    private void writeCloseTag(CharSequence name)
    {
        writeCharSequence(LEFT_CLOSE);
        writeCharSequence(name);
        writeCharSequence(RIGHT);
    }

    private void writeOpenTag(CharSequence name)
    {
        writeCharSequence(LEFT);
        writeCharSequence(name);
        writeCharSequence(RIGHT);
    }

    private void buildValidXml(final CharSequence value, final StringBuilder builder)
    {
        for (int i = 0, size = value.length(); i < size; i++)
        {
            final char ch = value.charAt(i);
            switch (ch)
            {
                case '"':
                    builder.append("&quot;");
                    break;

                case '&':
                    builder.append("&amp;");
                    break;

                case '\'':
                    builder.append("&apos;");
                    break;

                case '<':
                    builder.append("&lt;");
                    break;

                case '>':
                    builder.append("&gt;");
                    break;

                default:
                    builder.append(ch);
            }
        }
    }

    // ===============
    // Handling Output
    // ===============

    public void writeTo(OutputStream out) throws IOException
    {
        out.write(currentData, 0, length);
    }

    public String toString()
    {
        try
        {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            writeTo(stream);
            return new String(stream.toByteArray(), UTF_8);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
