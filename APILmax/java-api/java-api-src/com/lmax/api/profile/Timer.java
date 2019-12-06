package com.lmax.api.profile;

import com.lmax.api.internal.profile.MBeanHistogramTimer;
import com.lmax.api.order.Execution;
import com.lmax.api.order.ExecutionEventListener;
import com.lmax.api.order.Order;
import com.lmax.api.order.OrderEventListener;
import com.lmax.api.orderbook.OrderBookEvent;
import com.lmax.api.orderbook.OrderBookEventListener;
import com.lmax.api.reject.InstructionRejectedEvent;
import com.lmax.api.reject.InstructionRejectedEventListener;

/**
 * Factory class to construct timers for callback methods.
 */
public class Timer
{
    /**
     * Construct a timer for order events.
     * 
     * @param listener The real listener to delegate events to.
     * @param name The name associated to this timer.
     * @param timingListener The callback for timing events.
     * @return A listener that will time call to the real listener.
     */
    public static OrderEventListener create(final OrderEventListener listener, final String name, final TimingListener timingListener)
    {
        return new OrderEventListener()
        {
            @Override
            public void notify(Order order)
            {
                long t0 = System.nanoTime();
                listener.notify(order);
                long t1 = System.nanoTime();
                timingListener.notify(name, (t1 - t0));
            }
        };
    }
    
    /**
     * Construct an MBean based timer for order events.
     * 
     * @param listener The real listener to delgate too.
     * @return A listener that will time call to the real listener.
     */
    public static OrderEventListener forOrderEvents(OrderEventListener listener)
    {
        MBeanHistogramTimer timer = new MBeanHistogramTimer("Order");
        return create(listener, timer.register(), timer);
    }

    /**
     * Construct a timer for instruction rejected events.
     * 
     * @param listener The real listener to delegate events to.
     * @param name The name associated to this timer.
     * @param timingListener The callback for timing events.
     * @return A listener that will time call to the real listener.
     */
    public static InstructionRejectedEventListener create(final InstructionRejectedEventListener listener, final String name, final TimingListener timingListener)
    {
        return new InstructionRejectedEventListener()
        {
            @Override
            public void notify(InstructionRejectedEvent instructionRejected)
            {
                long t0 = System.nanoTime();
                listener.notify(instructionRejected);
                long t1 = System.nanoTime();
                timingListener.notify(name, (t1 - t0));
            }
        };
    }
    
    /**
     * Construct an MBean based timer for instruction rejected events.
     * 
     * @param listener The real listener to delgate too.
     * @return A listener that will time call to the real listener.
     */
    public static InstructionRejectedEventListener forInstructionRejectedEvents(InstructionRejectedEventListener listener)
    {
        MBeanHistogramTimer timer = new MBeanHistogramTimer("InstructionRejected");
        return create(listener, timer.register(), timer);
    }

    /**
     * Construct a timer for order book events.
     * 
     * @param listener The real listener to delegate events to.
     * @param name The name associated to this timer.
     * @param timingListener The callback for timing events.
     * @return A listener that will time call to the real listener.
     */
    public static OrderBookEventListener create(final OrderBookEventListener listener, final String name, final TimingListener timingListener)
    {
        return new OrderBookEventListener()
        {
            @Override
            public void notify(OrderBookEvent orderBookEvent)
            {
                long t0 = System.nanoTime();
                listener.notify(orderBookEvent);
                long t1 = System.nanoTime();
                timingListener.notify(name, (t1 - t0));
            }
        };
    }
    
    /**
     * Construct an MBean based timer for order book events.
     * 
     * @param listener The real listener to delgate too.
     * @return A listener that will time call to the real listener.
     */
    public static OrderBookEventListener forOrderBookEvents(OrderBookEventListener listener)
    {
        MBeanHistogramTimer timer = new MBeanHistogramTimer("OrderBook");
        return create(listener, timer.register(), timer);
    }

    /**
     * Construct a timer for execution events.
     * 
     * @param listener The real listener to delegate events to.
     * @param name The name associated to this timer.
     * @param timingListener The callback for timing events.
     * @return A listener that will time call to the real listener.
     */
    public static ExecutionEventListener create(final ExecutionEventListener listener, final String name, final TimingListener timingListener)
    {
        return new ExecutionEventListener()
        {
            @Override
            public void notify(Execution execution)
            {
                long t0 = System.nanoTime();
                listener.notify(execution);
                long t1 = System.nanoTime();
                timingListener.notify(name, (t1 - t0));
            }
        };
    }
    
    /**
     * Construct an MBean based timer for execution events.
     * 
     * @param listener The real listener to delgate too.
     * @return A listener that will time call to the real listener.
     */
    public static ExecutionEventListener forExecutionEvents(ExecutionEventListener listener)
    {
        MBeanHistogramTimer timer = new MBeanHistogramTimer("Execution");
        return create(listener, timer.register(), timer);
    }
}
