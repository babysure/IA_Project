package com.lmax.api.orderbook;

/**
 * Contains the meta-data for an instrument (Security Definition).
 */
public interface Instrument
{
    /**
     * Get the id of the instrument, also used a key for the order book on
     * order requests, etc.
     *
     * @return id.
     */
    long getId();

    /**
     * Get the name of the instrument, this is same value that is displayed
     * on the Lmax Trader UI.
     *
     * @return name of the instrument.
     */
    String getName();

    /**
     * Get information about the underlying instrument.
     *
     * @return underlying info.
     */
    UnderlyingInfo getUnderlying();

    /**
     * Get all date and time related information for the instrument.
     *
     * @return calendar.
     */
    CalendarInfo getCalendar();

    /**
     * Get all of the information relating to risk for this instrument.
     *
     * @return risk.
     */
    RiskInfo getRisk();

    /**
     * Get information relating the behaviour of the order book.
     *
     * @return order book.
     */
    OrderBookInfo getOrderBook();

    /**
     * Get information relating to the contract for this instrument.
     *
     * @return contract.
     */
    ContractInfo getContract();

    /**
     * Get information relating to the commercial agreements for this instrument.
     *
     * @return commercial info.
     */
    CommercialInfo getCommercial();
}
