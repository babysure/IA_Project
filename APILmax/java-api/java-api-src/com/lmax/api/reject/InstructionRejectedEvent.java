package com.lmax.api.reject;

/**
 * Event received when a instruction is rejected asynchronously.  E.g. INSUFFICIENT_LIQUIDITY
 * 
 */
public class InstructionRejectedEvent
{
    private final String instructionId;
    private final long accountId;
    private final long instrumentId;
    private final String reason;

    /**
     * Contruct the event.
     * 
     * @param instructionId The instructionId that this rejection was caused by
     * @param accountId The accountId of the user that submitted the request
     * @param instrumentId The instrumentId that the instruction was placed on
     * @param reason The reason for the reject
     */
    public InstructionRejectedEvent(String instructionId, long accountId, long instrumentId, String reason)
    {
        this.instructionId = instructionId;
        this.accountId = accountId;
        this.instrumentId = instrumentId;
        this.reason = reason;
    }

    /**
     * The instructionId that this rejection was caused by.
     * 
     * @return The instructionId that this rejection was caused by
     */
    public String getInstructionId()
    {
        return instructionId;
    }

    /**
     * The accountId of the user that submitted the request.
     * 
     * @return The accountId of the user that submitted the request
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * The instrumentId that the instruction was placed on.
     * 
     * @return The instrumentId that the instruction was placed on
     */
    public long getInstrumentId()
    {
        return instrumentId;
    }

    /**
     * The reason for the reject.
     * 
     * @return The reason for the reject, e.g. INSUFFICIENT_LIQUIDITY
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * Convert this event to a string.
     * 
     * @return The string representation of this event.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "InstructionRejectedEvent [instructionId=" + instructionId + ", accountId=" + accountId + ", instrumentId=" + instrumentId +
               ", reason=" + reason + "]";
    }
}
