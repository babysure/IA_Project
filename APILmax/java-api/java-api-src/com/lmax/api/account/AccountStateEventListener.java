package com.lmax.api.account;

/**
 *  Asynchronous listener for AccountStateEvents.
 */
public interface AccountStateEventListener
{
    /**
     * Called when the system notifies us of an account state change.
     *
     * @param accountStateEvent the event.
     */
    void notify(AccountStateEvent accountStateEvent);
}
