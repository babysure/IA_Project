package com.lmax.api.internal.protocol;

import java.util.ArrayList;
import java.util.List;

import com.lmax.api.internal.events.ExecutionBuilder;

import org.xml.sax.SAXException;

public class ExecutionsEventHandler extends MapBasedHandler
{
    private static final String EXECUTION_ID_NODE_NAME = "executionId";
    private static final String EXECUTION_NODE_NAME = "execution";
    private static final String PRICE_NODE_NAME = "price";
    private static final String QUANTITY_NODE_NAME = "quantity";
    private static final String ENCODED_EXECUTION_ID_NODE_NAME = "encodedExecutionId";
    private static final String ORDER_CANCELLED_NODE_NAME = "orderCancelled";

    private final List<ExecutionBuilder> executionBuilders;
    private ExecutionBuilder executionBuilder;

    public ExecutionsEventHandler(final String nodeName)
    {
        super(nodeName);
        addHandler(new Handler(EXECUTION_ID_NODE_NAME));
        addHandler(new Handler(PRICE_NODE_NAME));
        addHandler(new Handler(QUANTITY_NODE_NAME));
        addHandler(new Handler(ENCODED_EXECUTION_ID_NODE_NAME));

        executionBuilders = new ArrayList<>();
        executionBuilder = new ExecutionBuilder();
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (EXECUTION_NODE_NAME.equals(localName) || ORDER_CANCELLED_NODE_NAME.equals(localName))
        {
            if (EXECUTION_NODE_NAME.equals(localName))
            {
                executionBuilder.price(getFixedPointNumberValue(PRICE_NODE_NAME));
                executionBuilder.quantity(getFixedPointNumberValue(QUANTITY_NODE_NAME));
                executionBuilder.encodedExecutionId(getOptionalStringValue(ENCODED_EXECUTION_ID_NODE_NAME));
            }
            else if (ORDER_CANCELLED_NODE_NAME.equals(localName))
            {
                executionBuilder.cancelledQuantity(getFixedPointNumberValue(QUANTITY_NODE_NAME));
            }
            executionBuilder.executionId(getLongValue(EXECUTION_ID_NODE_NAME));
            executionBuilders.add(executionBuilder);
            executionBuilder = new ExecutionBuilder();
        }
    }

    public List<ExecutionBuilder> getExecutionBuilders()
    {
        return executionBuilders;
    }

    public void clear()
    {
        executionBuilders.clear();
    }
}
