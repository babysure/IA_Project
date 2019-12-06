package com.lmax.api.internal.xml;


import com.lmax.api.FixedPointNumber;

public interface StructuredWriter
{
    StructuredWriter startElement(CharSequence name);
    StructuredWriter value(CharSequence name, CharSequence value);
    StructuredWriter valueUTF8(CharSequence name, CharSequence value);
    StructuredWriter valueOrNone(String name, CharSequence value);

    StructuredWriter value(String name, long value);
    StructuredWriter valueOrNone(String string, long value, long nullValue);

    StructuredWriter value(String name, FixedPointNumber value);
    StructuredWriter valueOrNone(String name, FixedPointNumber value);
    
    StructuredWriter value(String name, boolean value);
    
    StructuredWriter writeEmptyTag(CharSequence name);
    StructuredWriter endElement(CharSequence name);
}
