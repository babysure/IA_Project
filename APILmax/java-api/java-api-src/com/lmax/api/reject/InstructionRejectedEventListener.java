package com.lmax.api.reject;

/**
 * Event interface for instruction rejections.
 */
public interface InstructionRejectedEventListener
{
    /**
     * Call when an instruction rejection is received.
     * 
     * @param instructionRejected The detail of the rejection
     */
    void notify(InstructionRejectedEvent instructionRejected);
}
