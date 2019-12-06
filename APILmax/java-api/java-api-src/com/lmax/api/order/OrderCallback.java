package com.lmax.api.order;

import com.lmax.api.FailureResponse;

/**
 * The callback used when the response is received for a market order request.
 */
public interface OrderCallback
{
    /**
     * Callback method when the system acknowledges that it has received the order.
     * Will supply the instruction id that the system use for this order.  This does
     * not indicate that the order has been placed on the exchange only that it has been
     * received.
     * 
     * @param instructionId The system assigned instruction id.
     */
    void onSuccess(String instructionId);

    /**
     * Callback method when the system rejects the market order request, the message
     * will contain information about why the request was rejected.  This will commonly
     * be a validation problem, e.g. invalid instrument, instrument closed, etc.  System
     * failures will also be reported through the onFailure call, setting the isSystemFailure
     * flag on the failure response.
     * 
     * @param failureResponse The response containing the failure to place order.
     */
    void onFailure(FailureResponse failureResponse);
}
