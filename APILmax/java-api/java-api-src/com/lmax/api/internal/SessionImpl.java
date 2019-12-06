package com.lmax.api.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import com.lmax.api.Callback;
import com.lmax.api.FailureResponse;
import com.lmax.api.Session;
import com.lmax.api.SessionDisconnectedListener;
import com.lmax.api.StreamFailureListener;
import com.lmax.api.SubscriptionRequest;
import com.lmax.api.UrlCallback;
import com.lmax.api.account.AccountDetails;
import com.lmax.api.account.AccountStateEventListener;
import com.lmax.api.account.AccountStateRequest;
import com.lmax.api.heartbeat.HeartbeatCallback;
import com.lmax.api.heartbeat.HeartbeatEventListener;
import com.lmax.api.heartbeat.HeartbeatRequest;
import com.lmax.api.internal.protocol.EventHandler;
import com.lmax.api.internal.protocol.EventStreamHandler;
import com.lmax.api.internal.protocol.HeartbeatResponseHandler;
import com.lmax.api.internal.protocol.LogoutRequest;
import com.lmax.api.internal.protocol.LogoutResponseHandler;
import com.lmax.api.internal.protocol.OrderResponseHandler;
import com.lmax.api.internal.protocol.SaxParserHelper;
import com.lmax.api.internal.protocol.SearchInstrumentResponseHandler;
import com.lmax.api.internal.xml.LoggingSaxParserHelper;
import com.lmax.api.internal.xml.StructuredWriter;
import com.lmax.api.marketdata.HistoricMarketDataRequest;
import com.lmax.api.order.AmendLimitPrice;
import com.lmax.api.order.AmendStopsRequest;
import com.lmax.api.order.CancelOrderRequest;
import com.lmax.api.order.ClosingOrderSpecification;
import com.lmax.api.order.ExecutionEventListener;
import com.lmax.api.order.LimitOrderSpecification;
import com.lmax.api.order.MarketOrderSpecification;
import com.lmax.api.order.OrderCallback;
import com.lmax.api.order.OrderEventListener;
import com.lmax.api.order.StopOrderSpecification;
import com.lmax.api.orderbook.HistoricMarketDataEventListener;
import com.lmax.api.orderbook.OrderBookEventListener;
import com.lmax.api.orderbook.OrderBookStatusEventListener;
import com.lmax.api.orderbook.SearchInstrumentCallback;
import com.lmax.api.orderbook.SearchInstrumentRequest;
import com.lmax.api.position.PositionEventListener;
import com.lmax.api.reject.InstructionRejectedEventListener;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class SessionImpl implements Session
{
    private final ConnectionFactory connectionFactory;
    private final HttpInvoker httpInvoker;
    private final EventHandler eventHandler;
    private final XmlParser saxParser;
    private final AccountDetails accountDetails;
    private final AtomicBoolean runFlag = new AtomicBoolean(false);
    private final boolean restartStreamOnFailure;
    private final EventStreamHandlerFactory eventStreamHandlerFactory;

    private volatile HttpURLConnection streamingConnection;
    private volatile StreamFailureListener streamFailureListener = new DefaultStreamFailureListener();
    private volatile SessionDisconnectedListener sessionDisconnectedListener = new DefaultSessionDisconnectedListener();

    public SessionImpl(final ConnectionFactory connectionFactory, final AccountDetails accountDetails, final String urlBase)
    {
        this(accountDetails, connectionFactory, new HttpInvoker(), new DefaultXmlParser(), new EventStreamHandlerFactory(), new EventHandler(urlBase), true);
    }

    //    @ExposedForTesting
    SessionImpl(final AccountDetails accountDetails,
                final ConnectionFactory connectionFactory,
                final HttpInvoker httpInvoker,
                final XmlParser saxParser,
                final EventStreamHandlerFactory eventStreamHandlerFactory,
                final EventHandler eventHandler,
                final boolean restartStreamOnFailure)
    {
        this.accountDetails = accountDetails;
        this.connectionFactory = connectionFactory;
        this.httpInvoker = httpInvoker;
        this.saxParser = saxParser;
        this.eventStreamHandlerFactory = eventStreamHandlerFactory;
        this.eventHandler = eventHandler;
        this.restartStreamOnFailure = restartStreamOnFailure;
    }

    @Override
    public void start()
    {
        if (!runFlag.compareAndSet(false, true))
        {
            throw new IllegalStateException("Can not call start twice concurrently on the same session");
        }

        final Request streamRequest = new Request()
        {
            @Override
            public String getUri()
            {
                return "/push/stream";
            }

            @Override
            public void writeTo(final StructuredWriter writer)
            {
            }
        };

        runEventLoop(streamRequest);
    }

    @Override
    public void stop()
    {
        runFlag.set(false);

        safelyDisconnect();
    }

    @Override
    public boolean isRunning()
    {
        return runFlag.get();
    }

    @Override
    public void logout(final Callback callback)
    {
        try
        {
            runFlag.set(false);
            final LogoutRequest request = new LogoutRequest();
            final HttpURLConnection connection = connectionFactory.createPostConnection(request);
            final Response response = httpInvoker.doPost(connection, request);

            if (Response.HTTP_OK != response.getHttpStatusCode())
            {
                callback.onFailure(new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode()));
                return;
            }

            final LogoutResponseHandler handler = new LogoutResponseHandler();
            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (handler.isOk())
            {
                callback.onSuccess();
            }
            else
            {
                callback.onFailure(new FailureResponse(false, handler.getMessageContent()));
            }
        }
        catch (final SAXException e)
        {
            callback.onFailure(new FailureResponse(e));
        }
        catch (final IOException e)
        {
            callback.onFailure(new FailureResponse(e));
        }
    }

    @Override
    public void placeMarketOrder(final MarketOrderSpecification marketOrderSpecification, final OrderCallback orderResponseCallback)
    {
        processRequest(marketOrderSpecification, orderResponseCallback);
    }

    @Override
    public void placeLimitOrder(final LimitOrderSpecification limitOrderSpecification, final OrderCallback orderResponseCallback)
    {
        processRequest(limitOrderSpecification, orderResponseCallback);
    }

    @Override
    public void placeStopOrder(final StopOrderSpecification stopOrderSpecification, final OrderCallback orderResponseCallback)
    {
        processRequest(stopOrderSpecification, orderResponseCallback);
    }

    @Override
    public void cancelOrder(final CancelOrderRequest cancelOrderRequest, final OrderCallback orderResponseCallback)
    {
        processRequest(cancelOrderRequest, orderResponseCallback);
    }

    @Override
    public void placeClosingOrder(final ClosingOrderSpecification closingOrderSpecification, final OrderCallback orderResponseCallback)
    {
        processRequest(closingOrderSpecification, orderResponseCallback);
    }

    @Override
    public void amendStops(final AmendStopsRequest amendStopLossProfitRequest, final OrderCallback orderResponseCallback)
    {
        processRequest(amendStopLossProfitRequest, orderResponseCallback);
    }

    @Override
    public void amendLimitPrice(final AmendLimitPrice amendLimitPriceRequest, final OrderCallback orderResponseCallback)
    {
        processRequest(amendLimitPriceRequest, orderResponseCallback);
    }

    @Override
    public void subscribe(final SubscriptionRequest subscriptionRequest, final Callback callback)
    {
        Response response;
        try
        {
            final HttpURLConnection connection = connectionFactory.createPostConnection(subscriptionRequest);
            response = httpInvoker.doPost(connection, subscriptionRequest);

            final SimpleHandler handler = new SimpleHandler();
            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (handler.isSuccess())
            {
                callback.onSuccess();
            }
            else
            {
                callback.onFailure(new FailureResponse(false, handler.getMessage()));
            }
        }
        catch (final SAXException e)
        {
            callback.onFailure(new FailureResponse(e));
        }
        catch (final IOException e)
        {
            callback.onFailure(new FailureResponse(e));
        }
    }

    @Override
    public void registerAccountStateEventListener(final AccountStateEventListener accountStateEventListener)
    {
        eventHandler.registerEventListener(accountStateEventListener);
    }

    @Override
    public void registerExecutionEventListener(final ExecutionEventListener executionListener)
    {
        eventHandler.registerEventListener(executionListener);
    }

    @Override
    public void registerHeartbeatListener(final HeartbeatEventListener heartbeatEventListener)
    {
        eventHandler.registerEventListener(heartbeatEventListener);
    }

    @Override
    public void registerHistoricMarketDataEventListener(final HistoricMarketDataEventListener listener)
    {
        eventHandler.registerEventListener(listener);
    }

    @Override
    public void registerInstructionRejectedEventListener(final InstructionRejectedEventListener eventListener)
    {
        eventHandler.registerEventListener(eventListener);
    }

    @Override
    public void registerOrderBookEventListener(final OrderBookEventListener orderBookEventListener)
    {
        eventHandler.registerEventListener(orderBookEventListener);
    }

    @Override
    public void registerOrderBookStatusEventListener(final OrderBookStatusEventListener eventListener)
    {
        eventHandler.registerEventListener(eventListener);
    }

    @Override
    public void registerOrderEventListener(final OrderEventListener orderEventListener)
    {
        eventHandler.registerEventListener(orderEventListener);
    }

    @Override
    public void registerPositionEventListener(final PositionEventListener positionEventListener)
    {
        eventHandler.registerEventListener(positionEventListener);
    }

    @Override
    public void registerStreamFailureListener(final StreamFailureListener aStreamFailureListener)
    {
        this.streamFailureListener = aStreamFailureListener;
    }

    @Override
    public void registerSessionDisconnectedListener(final SessionDisconnectedListener aSessionDisconnectedListener)
    {
        this.sessionDisconnectedListener = aSessionDisconnectedListener;
    }

    @Override
    public AccountDetails getAccountDetails()
    {
        return accountDetails;
    }

    @Override
    public void requestAccountState(final AccountStateRequest accountStateRequest, final Callback callback)
    {
        final FailureResponse result = sendRequestWithSimpleResponse(accountStateRequest);
        if (result != null)
        {
            callback.onFailure(result);
        }
        else
        {
            callback.onSuccess();
        }
    }

    @Override
    public void requestHistoricMarketData(final HistoricMarketDataRequest historicMarketDataRequest, final Callback callback)
    {
        final FailureResponse result = sendRequestWithSimpleResponse(historicMarketDataRequest);
        if (result != null)
        {
            callback.onFailure(result);
        }
        else
        {
            callback.onSuccess();
        }
    }


    @Override
    public void requestHeartbeat(final HeartbeatRequest heartbeatRequest, final HeartbeatCallback heartbeatCallback)
    {
        Response response;
        try
        {
            final HttpURLConnection connection = connectionFactory.createPostConnection(heartbeatRequest);
            response = httpInvoker.doPost(connection, heartbeatRequest);

            if (response.getHttpStatusCode() == HttpURLConnection.HTTP_FORBIDDEN)
            {
                sessionDisconnectedListener.notifySessionDisconnected();
                runFlag.set(false);
            }

            if (Response.HTTP_OK != response.getHttpStatusCode())
            {
                heartbeatCallback.onFailure(new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode()));
                return;
            }

            final HeartbeatResponseHandler handler = new HeartbeatResponseHandler();
            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (handler.isOk())
            {
                heartbeatCallback.onSuccess(handler.getToken());
            }
            else
            {
                heartbeatCallback.onFailure(new FailureResponse(false, handler.getMessage()));
            }
        }
        catch (final SAXException e)
        {
            heartbeatCallback.onFailure(new FailureResponse(e));
        }
        catch (final IOException e)
        {
            heartbeatCallback.onFailure(new FailureResponse(e));
        }
    }

    @Override
    public void searchInstruments(final SearchInstrumentRequest searchRequest, final SearchInstrumentCallback searchCallback)
    {
        final Response response;
        try
        {
            final HttpURLConnection connection = connectionFactory.createGetConnection(searchRequest);
            response = httpInvoker.doGet(connection);

            if (Response.HTTP_OK != response.getHttpStatusCode())
            {
                searchCallback.onFailure(new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode()));
                return;
            }

            final SearchInstrumentResponseHandler handler = new SearchInstrumentResponseHandler();
            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (handler.isOk())
            {
                searchCallback.onSuccess(handler.getInstruments(), handler.getHasMoreResults());
            }
            else
            {
                searchCallback.onFailure(new FailureResponse(false, handler.getMessage()));
            }
        }
        catch (final SAXException e)
        {
            searchCallback.onFailure(new FailureResponse(e));
        }
        catch (final IOException e)
        {
            searchCallback.onFailure(new FailureResponse(e));
        }
    }

    @Override
    public void openUrl(final URL url, final UrlCallback urlCallback)
    {
        try
        {
            final HttpURLConnection connection = connectionFactory.createConnection(url);
            connection.connect();
            if(Response.HTTP_OK == connection.getResponseCode())
            {
                InputStream inputStream = null;
                try
                {
                    inputStream = connection.getInputStream();
                    urlCallback.onSuccess(url, inputStream);
                }
                finally
                {
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }
            }
            else
            {
                urlCallback.onFailure(new FailureResponse(true, "HttpStatus: " + connection.getResponseCode()));
            }
        }
        catch (final IOException e)
        {
            urlCallback.onFailure(new FailureResponse(e));
        }
    }

    @Override
    public void setEventStreamDebug(Writer aWriter)
    {
        eventStreamHandlerFactory.setDebug(aWriter);
    }

    private void processRequest(final Request request, final OrderCallback orderResponseCallback)
    {
        try
        {
            final HttpURLConnection connection = connectionFactory.createPostConnection(request);
            final Response response = httpInvoker.doPost(connection, request);

            if (Response.HTTP_OK != response.getHttpStatusCode())
            {
                orderResponseCallback.onFailure(new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode()));
                return;
            }

            final OrderResponseHandler handler = new OrderResponseHandler();
            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (handler.isOk())
            {
                orderResponseCallback.onSuccess(handler.getInstructionId());
            }
            else
            {
                orderResponseCallback.onFailure(new FailureResponse(handler.getErrorMessage(), ""));
            }
        }
        catch (final SAXException e)
        {
            orderResponseCallback.onFailure(new FailureResponse(e));
        }
        catch (final IOException e)
        {
            orderResponseCallback.onFailure(new FailureResponse(e));
        }
    }

    private FailureResponse sendRequestWithSimpleResponse(final Request request)
    {
        FailureResponse failureResponse = null;
        Response response;
        try
        {
            final HttpURLConnection connection = connectionFactory.createPostConnection(request);
            response = httpInvoker.doPost(connection, request);

            if (Response.HTTP_OK != response.getHttpStatusCode())
            {
                failureResponse = new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode());
            }
            else
            {

                final SimpleHandler handler = new SimpleHandler();
                saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

                if (!handler.isSuccess())
                {
                    failureResponse = new FailureResponse(false, handler.getMessage());
                }
            }
        }
        catch (final SAXException e)
        {
            failureResponse = new FailureResponse(e);
        }
        catch (final IOException e)
        {
            failureResponse = new FailureResponse(e);
        }
        return failureResponse;
    }

    private void runEventLoop(final Request streamRequest)
    {
        do
        {
            try
            {
                streamingConnection = connectionFactory.createPostConnection(streamRequest);
                final EventStreamHandler eventStreamHandler = eventStreamHandlerFactory.newInstance(eventHandler);
                eventStreamHandler.parseEventStream(httpInvoker.openInputStream(streamingConnection, streamRequest));
            }
            catch (final UnexpectedHttpResponseCodeException e)
            {
                if (runFlag.get())
                {
                    if (e.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN)
                    {
                        sessionDisconnectedListener.notifySessionDisconnected();
                        runFlag.set(false);
                    }
                    else
                    {
                        streamFailureListener.notifyStreamFailure(e);
                    }
                }
            }
            catch (final IOException e)
            {
                notifyStreamFailureListener(e);
            }
            catch (final SAXException e)
            {
                notifyStreamFailureListener(e);
            }
            finally
            {
                safelyDisconnect();
            }
        }
        while (restartStreamOnFailure && runFlag.get());
    }

    private synchronized void safelyDisconnect()
    {
        if (streamingConnection != null)
        {
            streamingConnection.disconnect();
            streamingConnection = null;
        }
    }

    private void notifyStreamFailureListener(final Exception e)
    {
        if (runFlag.get())
        {
            streamFailureListener.notifyStreamFailure(e);
        }
    }

    static class EventStreamHandlerFactory
    {
        private volatile Writer writer;

        public EventStreamHandler newInstance(final EventHandler eventHandler)
        {
            if (writer != null)
            {
                return new EventStreamHandler(new LoggingSaxParserHelper(new SaxParserHelper(eventHandler), writer));
            }
            else
            {
                return new EventStreamHandler(new SaxParserHelper(eventHandler));
            }
        }

        public void setDebug(Writer aWriter)
        {
            this.writer = aWriter;
        }
    }
}
