package com.lmax.api.orderbook;

import java.util.List;

import com.lmax.api.FailureResponse;

/**
 *  Called back when a search instrument request succeeds or fails.
 */
public interface SearchInstrumentCallback
{
    /**
     * Indicates the instrument search was successfully requested.
     *
     * @param instruments a page of instruments returned by search.
     * @param hasMoreResults indicates the presence of more pages result.
     */
    void onSuccess(List<Instrument> instruments, boolean hasMoreResults);

    /**
     * Called when failure occurs.
     *
     * @param failureResponse Contains information about the failure.
     */
    void onFailure(FailureResponse failureResponse);
}
