package com.lmax.api.internal.orderbook;

import com.lmax.api.orderbook.UnderlyingInfo;


public class UnderlyingInfoImpl implements UnderlyingInfo
{
    private final String symbol;
    private final String isin;
    private final String assetClass;

    public UnderlyingInfoImpl(final String symbol, final String isin, final String assetClass)
    {
        this.symbol = symbol;
        this.isin = isin;
        this.assetClass = assetClass;
    }

    @Override
    public String getSymbol()
    {
        return symbol;
    }

    @Override
    public String getIsin()
    {
        return isin;
    }

    @Override
    public String getAssetClass()
    {
        return assetClass;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final UnderlyingInfoImpl that = (UnderlyingInfoImpl)o;

        if (assetClass != null ? !assetClass.equals(that.assetClass) : that.assetClass != null)
        { return false; }
        if (isin != null ? !isin.equals(that.isin) : that.isin != null)
        { return false; }
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (isin != null ? isin.hashCode() : 0);
        result = 31 * result + (assetClass != null ? assetClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UnderlyingInfo{" +
               "symbol='" + symbol + '\'' +
               ", isin='" + isin + '\'' +
               ", assetClass='" + assetClass + '\'' +
               '}';
    }
}
