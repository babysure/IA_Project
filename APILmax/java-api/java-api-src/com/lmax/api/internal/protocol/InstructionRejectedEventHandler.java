package com.lmax.api.internal.protocol;

import com.lmax.api.reject.InstructionRejectedEvent;
import com.lmax.api.reject.InstructionRejectedEventListener;

import org.xml.sax.SAXException;

public class InstructionRejectedEventHandler extends MapBasedHandler
{
    private static final String REASON = "reason";
    private static final String INSTRUMENT_ID = "instrumentId";
    private static final String ACCOUNT_ID = "accountId";
    private static final String INSTRUCTION_ID = "instructionId";
    private InstructionRejectedEventListener eventListener = null;
    
    public InstructionRejectedEventHandler()
    {
        super("instructionRejected");
        addHandler(INSTRUCTION_ID);
        addHandler(ACCOUNT_ID);
        addHandler(INSTRUMENT_ID);
        addHandler(REASON);
    }
    
    @Override
    public void endElement(String localName) throws SAXException
    {
        if (getElementName().equals(localName) && eventListener != null)
        {
            eventListener.notify(new InstructionRejectedEvent(getStringValue(INSTRUCTION_ID), getLongValue(ACCOUNT_ID),
                                                              getLongValue(INSTRUMENT_ID), getStringValue(REASON)));
        }
    }

    public void setListener(InstructionRejectedEventListener instructionRejectedEventListener)
    {
        this.eventListener = instructionRejectedEventListener;
    }
}
