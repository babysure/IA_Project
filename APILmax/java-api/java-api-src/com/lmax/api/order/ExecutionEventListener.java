package com.lmax.api.order;

/**
 * Asynchronous listener for ALL executions.
 */
public interface ExecutionEventListener
{
    /**
     * Called when an execution is received.
     * @param execution the execution
     */
    void notify(Execution execution);
}
