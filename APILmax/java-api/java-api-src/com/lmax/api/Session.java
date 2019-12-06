package com.lmax.api;

import java.io.Writer;
import java.net.URL;

import com.lmax.api.account.AccountDetails;
import com.lmax.api.account.AccountStateEventListener;
import com.lmax.api.account.AccountStateRequest;
import com.lmax.api.heartbeat.HeartbeatCallback;
import com.lmax.api.heartbeat.HeartbeatEventListener;
import com.lmax.api.heartbeat.HeartbeatRequest;
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

/**
 * The main interface for interacting with an LMAX session.  Sessions are thread safe, so after a successful login
 * the session object can be passed onto different threads.  The only restriction is that <code>start()</code>
 * can not be called more than twice.  The implementation enforces this by throwing an IllegalStateException if
 * start is called multiple times without stop being called in between.
 */
public interface Session
{
    /**
     * <p>
     * Start listening to events from the exchange.
     * </p>
     * <p>
     * Will throw an IllegalStateException if called twice without stop being called.
     * </p>
     */
    void start();

    /**
     * Stop listening to events from the exchange.
     */
    void stop();

    /**
     * Determine if the session is currently listening to events from the exchange.
     *
     * @return true if listening to events from the exchange.
     */
    boolean isRunning();

    /**
     * logout from the exchange.
     * You should stop listening to events first.
     *
     * @param callback call back to listen to logout events
     */
    void logout(Callback callback);

    /**
     * Place a market order onto the exchange.
     *
     * @param marketOrderSpecification The order request to place, should contain the instrumentId, quantity and time in force policy.
     * @param orderResponseCallback    Callback to handle acknowledgement that the request has been received by LMAX.
     */
    void placeMarketOrder(MarketOrderSpecification marketOrderSpecification, OrderCallback orderResponseCallback);

    /**
     * Place a limit order onto the exchange.
     *
     * @param limitOrderSpecification The order request to place, should contain the instrumentId, price, quantity and time in force policy.
     * @param orderResponseCallback   Callback to handle acknowledgement that the request has been received by LMAX.
     */
    void placeLimitOrder(LimitOrderSpecification limitOrderSpecification, OrderCallback orderResponseCallback);

    /**
     * Place a stop order onto the exchange.
     *
     * @param stopOrderSpecification The order request to place, should contain the instrumentId, stop price, quantity and time in force policy.
     * @param orderResponseCallback   Callback to handle acknowledgement that the request has been received by LMAX.
     */
    void placeStopOrder(StopOrderSpecification stopOrderSpecification, OrderCallback orderResponseCallback);

    /**
     * Cancel a limit order.
     *
     * @param cancelOrderRequest    The cancel request to place, should contain the instrumentId and the instructionId of the original
     *                              order.
     * @param orderResponseCallback Callback to handle acknowledgement that the cancel request has been received by the LMAX.
     */
    void cancelOrder(CancelOrderRequest cancelOrderRequest, OrderCallback orderResponseCallback);

    /**
     * Close the specified quantity on an instrument position.
     *
     * @param closingOrderSpecification  The specification containing the details of which position to close and for what quantity
     * @param orderResponseCallback Callback to handle acknowledgement that the cancel request has been received by the LMAX.
     */
    void placeClosingOrder(final ClosingOrderSpecification closingOrderSpecification, final OrderCallback orderResponseCallback);

    /**
     * Amend the stop loss and/or profit on an existing order.
     *
     * @param amendStopLossProfitRequest An amend order request containing a reference to the original order and the new stop loss/profit offsets
     * @param orderResponseCallback      Callback to handle acknowledgement that the amend request has been received by the LMAX.
     */
    void amendStops(AmendStopsRequest amendStopLossProfitRequest, OrderCallback orderResponseCallback);

    /**
     * Amend the limit price on a resting order
     *
     * @param amendLimitPriceRequest An amend order request containing a reference to the original order and the new price
     * @param orderResponseCallback      Callback to handle acknowledgement that the amend request has been received by the LMAX.
     */
    void amendLimitPrice(AmendLimitPrice amendLimitPriceRequest, OrderCallback orderResponseCallback);

    /**
     * <p>
     * Place a request to subscribe to events.
     * </p>
     * <p>
     * This method will invoke the success callback when the subscription request completes.
     * It will invoke the failure callback when the response contains errors.
     * </p>
     *
     * @param subscriptionRequest The details of the order books to subscribe to in this request.
     * @param callback   A callback to be invoked with the outcome of the request.
     */
    void subscribe(SubscriptionRequest subscriptionRequest, Callback callback);

    /**
     * Register for account state events.
     *
     * @param accountStateEventListener The listener for account state events.
     */
    void registerAccountStateEventListener(AccountStateEventListener accountStateEventListener);

    /**
     * <p>
     * Register for notification of the execution of your orders.
     * </p>
     * <p>
     * Events for ALL order you have placed will be delivered to this listener.
     * </p>
     *
     * @param executionListener The listener for ALL executions of your orders.
     */
    void registerExecutionEventListener(ExecutionEventListener executionListener);

    /**
     * Register a listener for heartbeats on the event stream.
     *
     * @param heartbeatEventListener The listener to notify when heartbeats arrive on the event stream.
     */
    void registerHeartbeatListener(HeartbeatEventListener heartbeatEventListener);

    /**
     * Register for historic market data events.
     *
     * @param historicMarketDataEventListener The listener for historic market data events.
     */
    void registerHistoricMarketDataEventListener(HistoricMarketDataEventListener historicMarketDataEventListener);

    /**
     * Register a listener rejected instructions.
     *
     * @param instructionRejectedEventListener
     *         the listener to notify when instructions are rejected.
     */
    void registerInstructionRejectedEventListener(InstructionRejectedEventListener instructionRejectedEventListener);

    /**
     * <p>
     * Register for order book events.
     * </p>
     * <p>
     * Events for ALL order books you subscribe to will be delivered to this listener.
     * </p>
     *
     * @param orderBookEventListener The listener for ALL order book events.
     */
    void registerOrderBookEventListener(OrderBookEventListener orderBookEventListener);

    /**
     * <p>
     * Register for order book status events.
     * </p>
     * <p>
     * Events for ALL order books you subscribe to will be delivered to this listener.
     * </p>
     *
     * @param eventListener The listener for ALL order book status events.
     */
    void registerOrderBookStatusEventListener(OrderBookStatusEventListener eventListener);

    /**
     * <p>
     * Register for notification of amendments to the state of your order.
     * </p>
     * <p>
     * Events for ALL orders you have placed will be delivered to this listener.
     * </p>
     *
     * @param orderEventListener The listener for ALL amendments to the state of your orders.
     */
    void registerOrderEventListener(OrderEventListener orderEventListener);

    /**
     * <p>
     * Register for position events.
     * </p>
     * <p>
     * Events for ALL positions you subscribe to will be delivered to this listener.
     * </p>
     *
     * @param positionEventListener The listener for ALL position events.
     */
    void registerPositionEventListener(PositionEventListener positionEventListener);

    /**
     * Register a listener for failures on the event stream.
     *
     * @param aStreamFailureListener the listener to notify when there are problems on the event stream.
     * @see StreamFailureListener
     */
    void registerStreamFailureListener(StreamFailureListener aStreamFailureListener);

    /**
     * Register a listener for session disconnected events (when the session has been logged out).
     *
     * @param sessionDisconnectedListener the listener to notify if the session has been disconnected.
     * @see SessionDisconnectedListener
     */
    void registerSessionDisconnectedListener(SessionDisconnectedListener sessionDisconnectedListener);

    /**
     * Get the account details for the user logged in this session.
     *
     * @return The account details
     */
    AccountDetails getAccountDetails();

    /**
     * Request an AccountStateEvent from the Lmax Trader platform.
     * This will push an AccountStateEvent out via the stream interface.
     *
     * @param accountStateRequest  The request to send.
     * @param callback Callback fired to indicate if the request has succeeded or failed.
     */
    void requestAccountState(AccountStateRequest accountStateRequest, Callback callback);

    /**
     * Request historic market data from the Lmax Trader platform.
     * This will push an HistoricMarketDataEvent out via the stream interface.
     *
     * @param historicMarketDataRequest The request to send.
     * @param callback Callback fired to indicate if the request has succeeded or failed.
     */
    void requestHistoricMarketData(HistoricMarketDataRequest historicMarketDataRequest, Callback callback);

    /**
     * Request a heartbeat from the Lmax Trader platform.
     * This will push a heartbeat event out via the stream interface.
     *
     * @param heartbeatRequest  The request to send.
     * @param heartBeatCallback Callback fired to indicate if the request has succeeded or failed.
     */
    void requestHeartbeat(HeartbeatRequest heartbeatRequest, HeartbeatCallback heartBeatCallback);

    /**
     * Request the instruments.  The instruments will not returned in this call, but will
     * cause the system to generate an asynchronous event(s) containing the information.
     * Instrument information is paged, on the callback will be a boolean value indicating
     * if there are any more values to be received.  The last instrumentId should be used
     * as the offsetInstrumentId on the subsequent request.  The results will be returned
     * ordered by instrument name.
     *
     * @param searchRequest  The request for instruments.
     * @param searchCallback Will be called if the request succeeds.
     */
    void searchInstruments(SearchInstrumentRequest searchRequest, SearchInstrumentCallback searchCallback);


    /**
     * Open a data url from the Lmax Trader platform.
     *
     * @param url The url of the file to open.
     * @param urlCallback Callback fired to indicate if the request has succeeded or failed.
     */
    void openUrl(final URL url, final UrlCallback urlCallback);

    /**
     * Set the write to push all of XML received by the event stream.
     * 
     * @param writer The writer to push data too.
     */
    void setEventStreamDebug(Writer writer);
}
