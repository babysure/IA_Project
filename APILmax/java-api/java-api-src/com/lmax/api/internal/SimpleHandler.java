package com.lmax.api.internal;

import com.lmax.api.internal.protocol.Handler;

import org.xml.sax.SAXException;

public class SimpleHandler extends Handler
{
    private final Handler statusElementHandler = new Handler();
    private final Handler messageHandler = new Handler();
    
    private boolean isSuccess;
    private String message;

    @Override
    public Handler getHandler(String qName)
    {
        if (STATUS.equals(qName))
        {
            return statusElementHandler;
        }
        else if(MESSAGE.equals(qName))
        {
            return messageHandler;
        }
        
        return this;
    }
    
    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (BODY.equals(localName))
        {
            isSuccess = OK.equals(statusElementHandler.getContent());
            if (!isSuccess)
            {
                message = messageHandler.getContent();
            }
        }
    }
    
    public String getMessage()
    {
        return message;
    }

    public boolean isSuccess()
    {
        return isSuccess;
    }
}