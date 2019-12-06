package com.lmax.api.internal.protocol;

import org.xml.sax.SAXException;

public class OrderResponseHandler extends MapBasedHandler
{
    private static final String FIELD_NAME_NODE_NAME = "fieldName";
    private static final String MESSAGE_NODE_NAME = "message";
    private static final String INSTRUCTION_ID_NODE_NAME = "instructionId";
    private static final String STATUS_NODE_NAME = "status";
    
    private boolean status;
    private String messageContent;

    public OrderResponseHandler()
    {
        super("header");
        addHandler(INSTRUCTION_ID_NODE_NAME);
        addHandler(STATUS_NODE_NAME);
        addHandler(MESSAGE_NODE_NAME);
        addHandler(FIELD_NAME_NODE_NAME);
    }
    
    public String getInstructionId()
    {
        return getStringValue(INSTRUCTION_ID_NODE_NAME);
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (BODY.equals(localName))
        {
            status = OK.equals(getStringValue(STATUS_NODE_NAME));
            if (status)
            {
            }
            else
            {
                final String fieldName = getStringValue(FIELD_NAME_NODE_NAME);
                messageContent = getStringValue(MESSAGE_NODE_NAME);
                if(fieldName.length() != 0)
                {
                    messageContent += ": " + fieldName;
                }
            }
        }
    }

    public String getErrorMessage()
    {
        return messageContent;
    }

    public boolean isOk()
    {
        return status;
    }
    
}
