package com.lmax.api.internal.protocol;

import com.lmax.api.heartbeat.HeartbeatEventListener;

import org.xml.sax.SAXException;

public class HeartbeatEventHandler extends MapBasedHandler
{
    private static final String TOKEN = "token";
    private static final String ACCOUNT_ID = "accountId";
    private volatile HeartbeatEventListener heartbeatEventListener;

    public HeartbeatEventHandler()
    {
        super("heartbeat");
        addHandler(ACCOUNT_ID);
        addHandler(TOKEN);
    }

    public void setListener(HeartbeatEventListener aHeartbeatEventListener)
    {
        this.heartbeatEventListener = aHeartbeatEventListener;
    }

    @Override
    public void endElement(String localName) throws SAXException
    {
        if (getElementName().equals(localName))
        {
            long accountId = getLongValue(ACCOUNT_ID);
            String token = getStringValue(TOKEN);

            if (heartbeatEventListener != null)
            {
                heartbeatEventListener.notify(accountId, token);
            }
        }
    }
}
