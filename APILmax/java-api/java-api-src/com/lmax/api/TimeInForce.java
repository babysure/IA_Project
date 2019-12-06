package com.lmax.api;

/**
 * The time in force policy applicable to orders.
 */
public enum TimeInForce
{
    FILL_OR_KILL("FillOrKill"),
    IMMEDIATE_OR_CANCEL("ImmediateOrCancel"),
    GOOD_FOR_DAY("GoodForDay"),
    GOOD_TIL_CANCELLED("GoodTilCancelled"),
    UNKNOWN("Unknown");

    private final String camelCase;

    TimeInForce(final String camelCase)
    {
        this.camelCase = camelCase;
    }

    /**
     * Life span of the order.
     *
     * @return The representation of this enum used by the protocol
     */
    public String asCamelCase()
    {
        return camelCase;
    }
}
