package com.lmax.api.internal;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public interface XmlParser
{
    void parse(InputSource is, DefaultHandler dh) throws SAXException, IOException;
}
