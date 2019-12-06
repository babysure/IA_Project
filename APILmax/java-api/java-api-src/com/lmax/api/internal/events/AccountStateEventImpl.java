package com.lmax.api.internal.events;

import java.util.Map;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.account.AccountStateEvent;
import com.lmax.api.account.Wallet;

public class AccountStateEventImpl implements AccountStateEvent
{
    private final long accountId;
    private final FixedPointNumber balance;
    private final FixedPointNumber cash;
    private final FixedPointNumber credit;
    private final FixedPointNumber availableFunds;
    private final FixedPointNumber availableToWithdraw;
    private final FixedPointNumber unrealisedProfitAndLoss;
    private final FixedPointNumber margin;
    private final Map<String, FixedPointNumber> walletNetOpenPositionByCurrency;
    private final Map<String, FixedPointNumber> walletBalanceByCurrency;
    private final Map<String, Wallet> wallets;

    public AccountStateEventImpl(
            long accountId,
            FixedPointNumber balance,
            FixedPointNumber cash,
            FixedPointNumber credit,
            FixedPointNumber availableFunds,
            FixedPointNumber availableToWithdraw,
            FixedPointNumber unrealisedProfitAndLoss,
            FixedPointNumber margin,
            Map<String, FixedPointNumber> walletNetOpenPositionByCurrency,
            Map<String, FixedPointNumber> walletBalanceByCurrency,
            Map<String, Wallet> wallets)
    {
        this.accountId = accountId;
        this.balance = balance;
        this.cash = cash == null ? FixedPointNumber.ZERO : cash;
        this.credit = credit == null ? FixedPointNumber.ZERO : credit;
        this.availableFunds = availableFunds;
        this.availableToWithdraw = availableToWithdraw;
        this.unrealisedProfitAndLoss = unrealisedProfitAndLoss;
        this.margin = margin;
        this.walletNetOpenPositionByCurrency = walletNetOpenPositionByCurrency;
        this.walletBalanceByCurrency = walletBalanceByCurrency;
        this.wallets = wallets;
    }

    @Override
    public long getAccountId()
    {
        return accountId;
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
    public FixedPointNumber getAvailableFunds()
    {
        return availableFunds;
    }

    @Override
    public FixedPointNumber getAvailableToWithdraw()
    {
        return availableToWithdraw;
    }

    @Override
    public FixedPointNumber getUnrealisedProfitAndLoss()
    {
        return unrealisedProfitAndLoss;
    }

    @Override
    public FixedPointNumber getMargin()
    {
        return margin;
    }

    @Override
    public Map<String, FixedPointNumber> getWallets()
    {
        return walletBalanceByCurrency;
    }

    @Override
    public Map<String, Wallet> getCurrencyWallets()
    {
        return wallets;
    }

    @Override
    public Map<String, FixedPointNumber> getNetOpenPositions()
    {
        return walletNetOpenPositionByCurrency;
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

        final AccountStateEventImpl that = (AccountStateEventImpl)o;

        if (accountId != that.accountId)
        {
            return false;
        }
        if (balance != null ? !balance.equals(that.balance) : that.balance != null)
        {
            return false;
        }
        if (cash != null ? !cash.equals(that.cash) : that.cash != null)
        {
            return false;
        }
        if (credit != null ? !credit.equals(that.credit) : that.credit != null)
        {
            return false;
        }
        if (availableFunds != null ? !availableFunds.equals(that.availableFunds) : that.availableFunds != null)
        {
            return false;
        }
        if (availableToWithdraw != null ? !availableToWithdraw.equals(that.availableToWithdraw) : that.availableToWithdraw != null)
        {
            return false;
        }
        if (unrealisedProfitAndLoss != null ? !unrealisedProfitAndLoss.equals(that.unrealisedProfitAndLoss) : that.unrealisedProfitAndLoss != null)
        {
            return false;
        }
        if (margin != null ? !margin.equals(that.margin) : that.margin != null)
        {
            return false;
        }
        if (walletNetOpenPositionByCurrency != null ? !walletNetOpenPositionByCurrency.equals(that.walletNetOpenPositionByCurrency) : that.walletNetOpenPositionByCurrency != null)
        {
            return false;
        }
        if (wallets != null ? !wallets.equals(that.wallets) : that.wallets != null)
        {
            return false;
        }
        return walletBalanceByCurrency != null ? walletBalanceByCurrency.equals(that.walletBalanceByCurrency) : that.walletBalanceByCurrency == null;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(accountId ^ (accountId >>> 32));
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (cash != null ? cash.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (availableFunds != null ? availableFunds.hashCode() : 0);
        result = 31 * result + (availableToWithdraw != null ? availableToWithdraw.hashCode() : 0);
        result = 31 * result + (unrealisedProfitAndLoss != null ? unrealisedProfitAndLoss.hashCode() : 0);
        result = 31 * result + (margin != null ? margin.hashCode() : 0);
        result = 31 * result + (walletNetOpenPositionByCurrency != null ? walletNetOpenPositionByCurrency.hashCode() : 0);
        result = 31 * result + (walletBalanceByCurrency != null ? walletBalanceByCurrency.hashCode() : 0);
        result = 31 * result + (wallets != null ? wallets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "AccountStateEventImpl{" +
               "accountId=" + accountId +
               ", balance=" + balance +
               ", cash=" + cash +
               ", credit=" + credit +
               ", availableFunds=" + availableFunds +
               ", availableToWithdraw=" + availableToWithdraw +
               ", unrealisedProfitAndLoss=" + unrealisedProfitAndLoss +
               ", margin=" + margin +
               ", walletNetOpenPositionByCurrency=" + walletNetOpenPositionByCurrency +
               ", walletBalanceByCurrency=" + walletBalanceByCurrency +
               ", wallets=" + wallets +
               '}';
    }
}
