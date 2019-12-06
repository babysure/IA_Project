package com.lmax.api.internal.protocol;

import java.util.HashMap;
import java.util.Map;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.account.Wallet;

import org.xml.sax.SAXException;

public class WalletsHandler extends MapBasedHandler
{
    private static final String WALLETS = "wallets";
    private static final String WALLET = "wallet";
    private static final String CURRENCY = "currency";
    private static final String BALANCE = "balance";
    private static final String CASH = "cash";
    private static final String CREDIT = "credit";
    private static final String NET_OPEN_POSITION = "netOpenPosition";

    private Map<String, FixedPointNumber> walletsBalances = new HashMap<>();
    private Map<String, FixedPointNumber> walletsNetOpenPositions = new HashMap<>();
    private Map<String, Wallet> wallets = new HashMap<>();

    public WalletsHandler()
    {
        super(WALLETS);
        addHandler(new Handler(CURRENCY));
        addHandler(new Handler(BALANCE));
        addHandler(new Handler(CASH));
        addHandler(new Handler(CREDIT));
        addHandler(new Handler(NET_OPEN_POSITION));
    }

    @Override
    public void endElement(final String localName) throws SAXException
    {
        if (WALLET.equals(localName))
        {
            final String currency = getStringValue(CURRENCY);
            final FixedPointNumber balance = getFixedPointNumberValue(BALANCE);
            final FixedPointNumber cash = getFixedPointNumberValue(CASH);
            final FixedPointNumber credit = getFixedPointNumberValue(CREDIT);
            final FixedPointNumber netOpenPosition = getFixedPointNumberValue(NET_OPEN_POSITION);

            wallets.put(currency, new WalletImpl(currency, balance, cash, credit, netOpenPosition));

            walletsBalances.put(currency, balance);
            if (netOpenPosition != null)
            {
                walletsNetOpenPositions.put(currency, netOpenPosition);
            }
            resetAll();
        }
    }

    public void clear()
    {
        walletsBalances = new HashMap<>();
        walletsNetOpenPositions = new HashMap<>();
        wallets = new HashMap<>();
    }

    @Deprecated
    public Map<String, FixedPointNumber> getWalletBalances()
    {
        return walletsBalances;
    }

    @Deprecated
    public Map<String, FixedPointNumber> getWalletNetOpenPositions()
    {
        return walletsNetOpenPositions;
    }

    public Map<String, Wallet> getWallets()
    {
        return wallets;
    }
}
