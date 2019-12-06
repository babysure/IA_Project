package com.lmax.api.internal.orderbook;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.orderbook.ContractInfo;


public class ContractInfoImpl implements ContractInfo
{
    private final String currency;
    private final FixedPointNumber unitPrice;
    private final String unitOfMeasure;
    private final FixedPointNumber contractSize;

    public ContractInfoImpl(final String currency, final FixedPointNumber unitPrice, final String unitOfMeasure, final FixedPointNumber contractSize)
    {
        this.currency = currency;
        this.unitPrice = unitPrice;
        this.unitOfMeasure = unitOfMeasure;
        this.contractSize = contractSize;
    }

    @Override
    public String getCurrency()
    {
        return currency;
    }

    @Override
    public FixedPointNumber getUnitPrice()
    {
        return unitPrice;
    }

    @Override
    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    @Override
    public FixedPointNumber getContractSize()
    {
        return contractSize;
    }

    @Override
    public String toString()
    {
        return "ContractInfo{" +
               "currency='" + currency + '\'' +
               ", unitPrice=" + unitPrice +
               ", unitOfMeasure='" + unitOfMeasure + '\'' +
               ", contractSize=" + contractSize +
               '}';
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final ContractInfoImpl that = (ContractInfoImpl)o;

        if (contractSize != null ? !contractSize.equals(that.contractSize) : that.contractSize != null)
        { return false; }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null)
        { return false; }
        if (unitOfMeasure != null ? !unitOfMeasure.equals(that.unitOfMeasure) : that.unitOfMeasure != null)
        { return false; }
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (unitOfMeasure != null ? unitOfMeasure.hashCode() : 0);
        result = 31 * result + (contractSize != null ? contractSize.hashCode() : 0);
        return result;
    }
}
