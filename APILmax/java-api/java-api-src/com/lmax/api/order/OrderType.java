package com.lmax.api.order;

/**
 * Enumerate the supported LMAX order types.
 */
public enum OrderType
{
    MARKET,
    LIMIT,
    CLOSE_OUT_ORDER,
    CLOSE_OUT_POSITION,
    STOP_LOSS_MARKET_ORDER,
    STOP_PROFIT_LIMIT_ORDER,
    SETTLEMENT_ORDER,
    OFF_ORDERBOOK,
    REVERSAL, 
    STOP_PROFIT_ORDER,
    STOP_LOSS_ORDER,
    TRAILING_STOP_LOSS_ORDER,
    
    /**
     * Used if the Java API does not support a particular order type.
     * Should not happen normally. 
     */
    UNKNOWN
}
