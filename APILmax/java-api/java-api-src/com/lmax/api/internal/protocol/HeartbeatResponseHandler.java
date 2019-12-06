package com.lmax.api.internal.protocol;

import org.xml.sax.SAXException;

public class HeartbeatResponseHandler extends MapBasedHandler
{
    private static final String TOKEN = "token";
    private boolean status;

    public HeartbeatResponseHandler()
    {
        super(BODY);
        addHandler(STATUS);
        addHandler(MESSAGE);
        addHandler(TOKEN);
    }

    public void endElement(final String localName) throws SAXException
    {
        if (BODY.equals(localName))
        {
            status = OK.equals(getStringValue(STATUS));
        }
    }

    public String getToken()
    {
        return getStringValue(TOKEN);
    }
    
    public boolean isOk()
    {
        return status;
    }

    public String getMessage()
    {
        return getStringValue(MESSAGE);
    }
}
