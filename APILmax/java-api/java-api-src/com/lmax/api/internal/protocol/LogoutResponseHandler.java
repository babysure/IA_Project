package com.lmax.api.internal.protocol;

import org.xml.sax.SAXException;

public class LogoutResponseHandler extends MapBasedHandler
{

    private boolean status;
    private String messageContent;

    public LogoutResponseHandler()
    {
        super(BODY);
        addHandler(STATUS);
        addHandler(MESSAGE);
    }


    public void endElement(final String localName) throws SAXException
    {
        if (BODY.equals(localName))
        {
            status = OK.equals(getStringValue(STATUS));
            if (!status)
            {
                messageContent = getStringValue(MESSAGE);
            }
        }

    }

    public String getMessageContent()
    {
        return messageContent;
    }

    public boolean isOk()
    {
        return status;
    }
    
}
