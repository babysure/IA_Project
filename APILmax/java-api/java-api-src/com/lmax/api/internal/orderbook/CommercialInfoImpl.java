package com.lmax.api.internal.orderbook;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.orderbook.CommercialInfo;

public class CommercialInfoImpl implements CommercialInfo
{
    private final FixedPointNumber minimumCommission;
    private final FixedPointNumber aggressiveCommissionRate;
    private final FixedPointNumber passiveCommissionRate;
    private final FixedPointNumber aggressiveCommissionPerContract;
    private final FixedPointNumber passiveCommissionPerContract;
    private final FixedPointNumber fundingPremiumPercentage;
    private final FixedPointNumber fundingReductionPercentage;
    private final String fundingBaseRate;
    private final FixedPointNumber longSwapPoints;
    private final FixedPointNumber shortSwapPoints;
    private final int dailyInterestRateBasis;

    public CommercialInfoImpl(final FixedPointNumber minimumCommission, final FixedPointNumber aggressiveCommissionRate, final FixedPointNumber passiveCommissionRate,
                              final FixedPointNumber aggressiveCommissionPerContract, final FixedPointNumber passiveCommissionPerContract,
                              final FixedPointNumber fundingPremiumPercentage, final FixedPointNumber fundingReductionPercentage, final String fundingBaseRate, final int dailyInterestRateBasis,
                              final FixedPointNumber longSwapPoints, final FixedPointNumber shortSwapPoints)
    {
        this.minimumCommission = minimumCommission;
        this.aggressiveCommissionRate = aggressiveCommissionRate;
        this.passiveCommissionRate = passiveCommissionRate;
        this.aggressiveCommissionPerContract = aggressiveCommissionPerContract;
        this.passiveCommissionPerContract = passiveCommissionPerContract;
        this.fundingPremiumPercentage = fundingPremiumPercentage;
        this.fundingReductionPercentage = fundingReductionPercentage;
        this.fundingBaseRate = fundingBaseRate;
        this.dailyInterestRateBasis = dailyInterestRateBasis;
        this.longSwapPoints = longSwapPoints;
        this.shortSwapPoints = shortSwapPoints;
    }

    @Override
    public FixedPointNumber getMinimumCommission()
    {
        return minimumCommission;
    }

    @Override
    public FixedPointNumber getAggressiveCommissionRate()
    {
        return aggressiveCommissionRate;
    }

    @Override
    public FixedPointNumber getPassiveCommissionRate()
    {
        return passiveCommissionRate;
    }

    @Override
    public FixedPointNumber getAggressiveCommissionPerContract()
    {
        return aggressiveCommissionPerContract;
    }

    @Override
    public FixedPointNumber getPassiveCommissionPerContract()
    {
        return passiveCommissionPerContract;
    }

    @Override
    public FixedPointNumber getFundingPremiumPercentage()
    {
        return fundingPremiumPercentage;
    }

    @Override
    public FixedPointNumber getFundingReductionPercentage()
    {
        return fundingReductionPercentage;
    }

    @Override
    public String getFundingBaseRate()
    {
        return fundingBaseRate;
    }

    @Override
    public int getDailyInterestRateBasis()
    {
        return dailyInterestRateBasis;
    }

    @Override
    public FixedPointNumber getLongSwapPoints()
    {
        return longSwapPoints;
    }

    @Override
    public FixedPointNumber getShortSwapPoints()
    {
        return shortSwapPoints;
    }

    @Override
    @Deprecated
    public FixedPointNumber getFundingRate()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "CommercialInfoImpl{" +
               "minimumCommission=" + minimumCommission +
               ", aggressiveCommissionRate=" + aggressiveCommissionRate +
               ", passiveCommissionRate=" + passiveCommissionRate +
               ", aggressiveCommissionPerContract=" + aggressiveCommissionPerContract +
               ", passiveCommissionPerContract=" + passiveCommissionPerContract +
               ", fundingPremiumPercentage=" + fundingPremiumPercentage +
               ", fundingReductionPercentage=" + fundingReductionPercentage +
               ", fundingBaseRate='" + fundingBaseRate + '\'' +
               ", dailyInterestRateBasis=" + dailyInterestRateBasis +
               '}';
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final CommercialInfoImpl that = (CommercialInfoImpl)o;

        if (dailyInterestRateBasis != that.dailyInterestRateBasis)
        { return false; }
        if (aggressiveCommissionPerContract != null ? !aggressiveCommissionPerContract.equals(that.aggressiveCommissionPerContract) : that.aggressiveCommissionPerContract != null)
        { return false; }
        if (aggressiveCommissionRate != null ? !aggressiveCommissionRate.equals(that.aggressiveCommissionRate) : that.aggressiveCommissionRate != null)
        { return false; }
        if (fundingBaseRate != null ? !fundingBaseRate.equals(that.fundingBaseRate) : that.fundingBaseRate != null)
        { return false; }
        if (fundingPremiumPercentage != null ? !fundingPremiumPercentage.equals(that.fundingPremiumPercentage) : that.fundingPremiumPercentage != null)
        { return false; }
        if (fundingReductionPercentage != null ? !fundingReductionPercentage.equals(that.fundingReductionPercentage) : that.fundingReductionPercentage != null)
        { return false; }
        if (minimumCommission != null ? !minimumCommission.equals(that.minimumCommission) : that.minimumCommission != null)
        { return false; }
        if (passiveCommissionPerContract != null ? !passiveCommissionPerContract.equals(that.passiveCommissionPerContract) : that.passiveCommissionPerContract != null)
        { return false; }
        if (passiveCommissionRate != null ? !passiveCommissionRate.equals(that.passiveCommissionRate) : that.passiveCommissionRate != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = minimumCommission != null ? minimumCommission.hashCode() : 0;
        result = 31 * result + (aggressiveCommissionRate != null ? aggressiveCommissionRate.hashCode() : 0);
        result = 31 * result + (passiveCommissionRate != null ? passiveCommissionRate.hashCode() : 0);
        result = 31 * result + (aggressiveCommissionPerContract != null ? aggressiveCommissionPerContract.hashCode() : 0);
        result = 31 * result + (passiveCommissionPerContract != null ? passiveCommissionPerContract.hashCode() : 0);
        result = 31 * result + (fundingPremiumPercentage != null ? fundingPremiumPercentage.hashCode() : 0);
        result = 31 * result + (fundingReductionPercentage != null ? fundingReductionPercentage.hashCode() : 0);
        result = 31 * result + (fundingBaseRate != null ? fundingBaseRate.hashCode() : 0);
        result = 31 * result + dailyInterestRateBasis;
        return result;
    }
}
