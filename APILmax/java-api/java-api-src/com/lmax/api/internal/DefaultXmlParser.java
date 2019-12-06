package com.lmax.api.internal;

import java.io.IOException;

import com.lmax.api.internal.xml.ElementOnlyXmlReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultXmlParser implements XmlParser
{
    public void parse(final InputSource is, final DefaultHandler dh) throws SAXException, IOException
    {
        ElementOnlyXmlReader xmlReader = new ElementOnlyXmlReader();
        xmlReader.setContentHandler(dh);
        xmlReader.parse(is.getCharacterStream());
    }
}
