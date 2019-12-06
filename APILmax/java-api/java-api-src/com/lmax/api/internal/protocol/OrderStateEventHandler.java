package com.lmax.api.internal.protocol;

import java.util.List;

import com.lmax.api.internal.events.ExecutionBuilder;
import com.lmax.api.internal.events.OrderEventBuilder;
import com.lmax.api.order.Execution;
import com.lmax.api.order.ExecutionEventListener;
import com.lmax.api.order.Order;
import com.lmax.api.order.OrderEventListener;

import org.xml.sax.SAXException;

public class OrderStateEventHandler extends MapBasedHandler
{
    private static final String INSTRUCTION_ID_NODE_NAME = "instructionId";
    private static final String ORIGINAL_INSTRUCTION_ID_NODE_NAME = "originalInstructionId";
    private static final String ORDER_ID_NODE_NAME = "orderId";
    private static final String INSTRUMENT_ID_NODE_NAME = "instrumentId";
    private static final String ACCOUNT_ID_NODE_NAME = "accountId";
    private static final String QUANTITY_NODE_NAME = "quantity";
    private static final String MATCHED_QUANTITY_NODE_NAME = "matchedQuantity";
    private static final String CANCELLED_QUANTITY_NODE_NAME = "cancelledQuantity";
    private static final String ORDER_TYPE_NODE_NAME = "orderType";

    private static final String PRICE_NODE_NAME = "price";
    private static final String STOP_REFERENCE_PRICE_NODE_NAME = "stopReferencePrice";
    private static final String STOP_LOSS_OFFSET_NODE_NAME = "stopLossOffset";
    private static final String TRAILING_STOP_LOSS_NODE_NAME = "trailing";
    private static final String STOP_PROFIT_OFFSET_NODE_NAME = "stopProfitOffset";
    private static final String COMMISSION_NODE_NAME = "commission";
    private static final String TIME_IN_FORCE_NODE_NAME = "timeInForce";

    private final ExecutionsEventHandler executionsEventHandler;
    private OrderEventBuilder orderEventBuilder = new OrderEventBuilder();
    private ExecutionEventListener executionEventListener;
    private OrderEventListener orderEventListener;

    public OrderStateEventHandler()
    {
        super("order");

        executionsEventHandler = new ExecutionsEventHandler("executions");

        addHandler(INSTRUCTION_ID_NODE_NAME);
        addHandler(ORIGINAL_INSTRUCTION_ID_NODE_NAME);
        addHandler(ORDER_ID_NODE_NAME);
        addHandler(INSTRUMENT_ID_NODE_NAME);
        addHandler(ACCOUNT_ID_NODE_NAME);
        addHandler(QUANTITY_NODE_NAME);
        addHandler(MATCHED_QUANTITY_NODE_NAME);
        addHandler(CANCELLED_QUANTITY_NODE_NAME);
        addHandler(PRICE_NODE_NAME);
        addHandler(ORDER_TYPE_NODE_NAME);
        addHandler(STOP_REFERENCE_PRICE_NODE_NAME);
        addHandler(STOP_LOSS_OFFSET_NODE_NAME);
        addHandler(TRAILING_STOP_LOSS_NODE_NAME);
        addHandler(STOP_PROFIT_OFFSET_NODE_NAME);
        addHandler(COMMISSION_NODE_NAME);
        addHandler(TIME_IN_FORCE_NODE_NAME);

        addHandler(executionsEventHandler);
    }

    public void setListener(final ExecutionEventListener anExecutionEventListener)
    {
        executionEventListener = anExecutionEventListener;
    }

    public void setListener(final OrderEventListener anOrderEventListener)
    {
        this.orderEventListener = anOrderEventListener;
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if ("order".equals(localName))
        {
            orderEventBuilder.instructionId(getStringValue(INSTRUCTION_ID_NODE_NAME));
            orderEventBuilder.originalInstructionId(getOptionalStringValue(ORIGINAL_INSTRUCTION_ID_NODE_NAME));
            orderEventBuilder.orderId(getStringValue(ORDER_ID_NODE_NAME));
            orderEventBuilder.instrumentId(getLongValue(INSTRUMENT_ID_NODE_NAME));
            orderEventBuilder.accountId(getLongValue(ACCOUNT_ID_NODE_NAME));
            orderEventBuilder.quantity(getFixedPointNumberValue(QUANTITY_NODE_NAME));
            orderEventBuilder.filledQuantity(getFixedPointNumberValue(MATCHED_QUANTITY_NODE_NAME));
            orderEventBuilder.cancelledQuantity(getFixedPointNumberValue(CANCELLED_QUANTITY_NODE_NAME));
            orderEventBuilder.price(getFixedPointNumberValue(PRICE_NODE_NAME));
            orderEventBuilder.orderType(getStringValue(ORDER_TYPE_NODE_NAME));
            orderEventBuilder.stopReferencePrice(getFixedPointNumberValue(STOP_REFERENCE_PRICE_NODE_NAME));
            orderEventBuilder.stopLossOffset(getFixedPointNumberValue(STOP_LOSS_OFFSET_NODE_NAME));
            orderEventBuilder.trailingStopLoss(getBooleanValue(TRAILING_STOP_LOSS_NODE_NAME));
            orderEventBuilder.stopProfitOffset(getFixedPointNumberValue(STOP_PROFIT_OFFSET_NODE_NAME));
            orderEventBuilder.commission(getFixedPointNumberValue(COMMISSION_NODE_NAME));
            orderEventBuilder.timeInForce(getStringValue(TIME_IN_FORCE_NODE_NAME));

            final Order order = orderEventBuilder.newInstance();
            if (orderEventListener != null && shouldEmitOrder(order))
            {
                orderEventListener.notify(order);
            }
            orderEventBuilder = new OrderEventBuilder();
            notifyExecutions(order);
            resetAll();
        }
    }

    private void notifyExecutions(final Order order)
    {
        if (executionEventListener != null)
        {
            final List<ExecutionBuilder> executionBuilders = executionsEventHandler.getExecutionBuilders();
            for (final ExecutionBuilder executionBuilder : executionBuilders)
            {
                executionBuilder.order(order);
                final Execution execution = executionBuilder.newInstance();
                if (isExecutionForOrder(order, execution))
                {
                    executionEventListener.notify(execution);
                }
            }
            executionsEventHandler.clear();
        }
    }

    @SuppressWarnings({"SimplifiableConditionalExpression"})
    private boolean shouldEmitOrder(final Order order)
    {
        return executionsEventHandler.getExecutionBuilders().size() != 0 ?
               isExecutionForOrder(order, executionsEventHandler.getExecutionBuilders().get(0).newInstance()) :
               true;
    }

    private boolean isExecutionForOrder(final Order order, final Execution execution)
    {
        return order.getQuantity().signum() == execution.getQuantity().signum() ||
               order.getQuantity().signum() == execution.getCancelledQuantity().signum();
    }
}
