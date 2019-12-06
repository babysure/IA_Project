package com.lmax.api.internal.protocol;

import java.util.Objects;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.account.Wallet;

public class WalletImpl implements Wallet
{
    private final String currency;
    private final FixedPointNumber balance;
    private final FixedPointNumber cash;
    private final FixedPointNumber credit;
    private final FixedPointNumber netOpenPosition;

    public WalletImpl(
            final String currency,
            final FixedPointNumber balance,
            final FixedPointNumber cash,
            final FixedPointNumber credit,
            final FixedPointNumber netOpenPosition)
    {
        this.currency = currency;
        this.balance = balance;
        this.cash = cash == null ? FixedPointNumber.ZERO : cash;
        this.credit = credit == null ? FixedPointNumber.ZERO : credit;
        this.netOpenPosition = netOpenPosition == null ? FixedPointNumber.ZERO : netOpenPosition;
    }

    @Override
    public String getCurrency()
    {
        return currency;
    }

    @Override
    public FixedPointNumber getBalance()
    {
        return balance;
    }

    @Override
    public FixedPointNumber getCash()
    {
        return cash;
    }

    @Override
    public FixedPointNumber getCredit()
    {
        return credit;
    }

    @Override
    public FixedPointNumber getNetOpenPosition()
    {
        return netOpenPosition;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final WalletImpl wallet = (WalletImpl)o;
        return Objects.equals(currency, wallet.currency) &&
               Objects.equals(balance, wallet.balance) &&
               Objects.equals(cash, wallet.cash) &&
               Objects.equals(credit, wallet.credit) &&
               Objects.equals(netOpenPosition, wallet.netOpenPosition);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(currency, balance, cash, credit, netOpenPosition);
    }

    @Override
    public String toString()
    {
        return "Wallet{" +
               "currency='" + currency + '\'' +
               ", balance=" + balance +
               ", cash=" + cash +
               ", credit=" + credit +
               ", netOpenPosition=" + netOpenPosition +
               '}';
    }

}
