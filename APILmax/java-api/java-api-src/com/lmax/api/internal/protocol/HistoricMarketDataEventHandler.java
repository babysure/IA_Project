package com.lmax.api.internal.protocol;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.lmax.api.internal.events.HistoricMarketDataEventImpl;
import com.lmax.api.orderbook.HistoricMarketDataEventListener;

import org.xml.sax.SAXException;

public class HistoricMarketDataEventHandler extends MapBasedHandler
{
    private static final String HISTORIC_MARKET_DATA_ELEMENT = "historicMarketData";
    private static final String INSTRUCTION_ID_ELEMENT = "instructionId";
    private final UrlHandler urlHandler;
    private HistoricMarketDataEventListener historicMarketDataEventListener;

    public HistoricMarketDataEventHandler()
    {
        super(HISTORIC_MARKET_DATA_ELEMENT);

        urlHandler = new UrlHandler();

        addHandler(INSTRUCTION_ID_ELEMENT);
        addHandler(urlHandler);
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (HISTORIC_MARKET_DATA_ELEMENT.equals(localName))
        {
            if (historicMarketDataEventListener != null)
            {
                final String instructionId = getStringValue(INSTRUCTION_ID_ELEMENT);
                final List<URL> urls = urlHandler.getUrls();
                historicMarketDataEventListener.notify(new HistoricMarketDataEventImpl(instructionId, urls));
            }
            urlHandler.clear();
        }
    }

    public void setListener(final HistoricMarketDataEventListener listener)
    {
        this.historicMarketDataEventListener = listener;
    }

    private class UrlHandler extends Handler
    {
        private static final String URL_ELEMENT = "url";
        private List<URL> urls = new ArrayList<>();

        public UrlHandler()
        {
           super(URL_ELEMENT);
        }

        @Override
        public void endElement(final String localName) throws SAXException
        {
            if (URL_ELEMENT.equals(localName))
            {
                try
                {
                    urls.add(new URL(getContent()));
                }
                catch (final MalformedURLException e)
                {
                    throw new SAXException(e);
                }
            }
        }

        public List<URL> getUrls()
        {
            return urls;
        }

        public void clear()
        {
            urls = new ArrayList<>();
        }
    }
}
