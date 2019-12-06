package com.lmax.api.account;

import java.util.Map;

import com.lmax.api.FixedPointNumber;

/**
 * A event that contains all of the top level information about an account's
 * current state.
 */
public interface AccountStateEvent
{
    /**
     * Get the account id that this event pertains to.
     * @return  account id
     */
    long getAccountId();

    /**
     * Get the accounts current balance.
     * @return account balance
     */
    FixedPointNumber getBalance();

    /**
     * Get the accounts current Cash balance.
     * @return cash
     */
    FixedPointNumber getCash();

    /**
     * Get the accounts current credit balance.
     * @return credit
     */
    FixedPointNumber getCredit();

    /**
     * Get the account's available funds.
     * @return available funds
     */
    FixedPointNumber getAvailableFunds();

    /**
     * Get the amount that this account is available to withdraw.
     * @return available to withdraw
     */
    FixedPointNumber getAvailableToWithdraw();

    /**
     * Get a signed amount that is the account's unrealised profit (or loss).
     * @return unrealised profit (or loss)
     */
    FixedPointNumber getUnrealisedProfitAndLoss();

    /**
     * Get the account's total margin.
     * @return margin
     */
    FixedPointNumber getMargin();

    /**
     * Get the account's balances by currency.  The map is keyed by
     * 3 letter currency symbol, e.g. GBP.
     * @return map of currency code to wallet balance
     * @deprecated replaced by {@link #getCurrencyWallets()}
     */
    @Deprecated
    Map<String, FixedPointNumber> getWallets();

    /**
     * Get the net open currency positions (only applicable to accounts using net margin).
     * The map is keyed by 3 letter currency symbol, e.g. GBP.
     * @return map of currency code to net open position
     * @deprecated replaced by {@link #getCurrencyWallets()}
     */
    @Deprecated
    Map<String, FixedPointNumber> getNetOpenPositions();

    /**
     * Get the account's wallets by currency.  The map is keyed by
     * 3 letter currency symbol, e.g. GBP.
     * @return map of currency code to wallet object
     */
    Map<String, Wallet> getCurrencyWallets();
}
