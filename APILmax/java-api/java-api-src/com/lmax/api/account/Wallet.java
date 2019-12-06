package com.lmax.api.account;

import com.lmax.api.FixedPointNumber;

public interface Wallet
{
    /**
     * Get the currency for the wallet.
     *
     * @return wallet currency
     */
    String getCurrency();

    /**
     * Get the accounts current balance.
     *
     * @return account balance
     */
    FixedPointNumber getBalance();

    /**
     * Get the accounts current Cash balance.
     *
     * @return cash
     */
    FixedPointNumber getCash();

    /**
     * Get the accounts current credit balance.
     *
     * @return credit
     */
    FixedPointNumber getCredit();

    /**
     * Get the net open currency position (only applicable to accounts using net margin).
     *
     * @return net open position
     */
    FixedPointNumber getNetOpenPosition();
}
