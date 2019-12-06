package com.lmax.api.internal.protocol;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.account.AccountStateEvent;
import com.lmax.api.account.AccountStateEventListener;
import com.lmax.api.internal.events.AccountStateEventImpl;

import org.xml.sax.SAXException;

public class AccountStateEventHandler extends MapBasedHandler
{
    private static final String ACCOUNT_STATE = "accountState";
    private static final String ACCOUNT_ID = "accountId";
    private static final String BALANCE = "balance";
    private static final String CASH = "cash";
    private static final String CREDIT = "credit";
    private static final String AVAILABLE_FUNDS = "availableFunds";
    private static final String AVAILABLE_TO_WITHDRAW = "availableToWithdraw";
    private static final String UNREALISED_PROFIT_AND_LOSS = "unrealisedProfitAndLoss";
    private static final String MARGIN = "margin";

    private AccountStateEventListener listener;
    private final WalletsHandler walletsHandler;

    public AccountStateEventHandler()
    {
        super(ACCOUNT_STATE);
        addHandler(ACCOUNT_ID);
        addHandler(BALANCE);
        addHandler(CASH);
        addHandler(CREDIT);
        addHandler(AVAILABLE_FUNDS);
        addHandler(AVAILABLE_TO_WITHDRAW);
        addHandler(UNREALISED_PROFIT_AND_LOSS);
        addHandler(MARGIN);

        walletsHandler = new WalletsHandler();
        addHandler(walletsHandler);
    }

    public void setListener(final AccountStateEventListener listener)
    {
        this.listener = listener;
    }

    public void endElement(final String tagName) throws SAXException
    {
        if (ACCOUNT_STATE.equals(tagName))
        {
            final long accountId = getLongValue(ACCOUNT_ID);
            final FixedPointNumber balance = getFixedPointNumberValue(BALANCE);
            final FixedPointNumber cash = getFixedPointNumberValue(CASH);
            final FixedPointNumber credit = getFixedPointNumberValue(CREDIT);
            final FixedPointNumber availableFunds = getFixedPointNumberValue(AVAILABLE_FUNDS);
            final FixedPointNumber availableToWithdraw = getFixedPointNumberValue(AVAILABLE_TO_WITHDRAW);
            final FixedPointNumber unrealisedProfitAndLoss = getFixedPointNumberValue(UNREALISED_PROFIT_AND_LOSS);
            final FixedPointNumber margin = getFixedPointNumberValue(MARGIN);

            AccountStateEvent accountStateEvent = new AccountStateEventImpl(accountId,
                                                                        balance,
                                                                        cash,
                                                                        credit,
                                                                        availableFunds,
                                                                        availableToWithdraw,
                                                                        unrealisedProfitAndLoss,
                                                                        margin,
                                                                        walletsHandler.getWalletNetOpenPositions(),
                                                                        walletsHandler.getWalletBalances(),
                                                                        walletsHandler.getWallets()
            );

            if (listener != null)
            {
                listener.notify(accountStateEvent);
            }
            walletsHandler.clear();
        }
    }
}
