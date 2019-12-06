package com.lmax.api.orderbook;

import com.lmax.api.FixedPointNumber;

/**
 * Hold information pertaining to the commercial detail of this instrument.
 */

public interface CommercialInfo
{
    /**
     * Get the minimum commission applied for a trade on this instrument.
     *
     * @return minimum commission.
     */
    FixedPointNumber getMinimumCommission();

    /**
     * Get the aggressive commission rate, may be null if commission is
     * charged per contract.  The commission charge when the order is
     * on the aggressive side of the trade.
     *
     * @return aggressive commission rate.
     */
    FixedPointNumber getAggressiveCommissionRate();

    /**
     * Get the passive commission rate, may be null if commission is
     * charged per contract.  The commission charge when the order is
     * on the passive side of the trade.
     *
     * @return passive commission rate.
     */
    FixedPointNumber getPassiveCommissionRate();

    /**
     * Get the aggressive commission per contract, may be null if commission is
     * charged using a rate.  The commission charge when the order is
     * on the aggressive side of the trade.
     *
     * @return aggressive commission per contract.
     */
    FixedPointNumber getAggressiveCommissionPerContract();

    /**
     * Get the passive commission per contract, may be null if commission is
     * charged using a rate.  The commission charge when the order is
     * on the passive side of the trade.
     *
     * @return passive commission per contract.
     */
    FixedPointNumber getPassiveCommissionPerContract();

    /**
     * Get the base rate used for funding this instrument.
     *
     * @return funding base rate.
     */
    String getFundingBaseRate();

    /**
     * Get the percentage premium added to the funding base rate for overnight funding of long positions on non-FX instruments.
     *
     * @return funding rate premium for long positions
     * @see #getFundingBaseRate()
     */
    FixedPointNumber getFundingPremiumPercentage();

    /**
     * Get the percentage reduction applied to the funding base rate for overnight funding of short positions on non-FX instruments.
     *
     * @return funding rate reduction for short positions
     * @see #getFundingBaseRate()
     */
    FixedPointNumber getFundingReductionPercentage();

    /**
     * Get the number of days per year used to calculate the daily interest charged for funding.
     *
     * @return interest rate day basis.
     */
    int getDailyInterestRateBasis();

    /**
     * Get the swap points used to calculate overnight interest swap charges for long positions on FX instruments.
     *
     * @return swap points for long positions
     */
    FixedPointNumber getLongSwapPoints();

    /**
     * Get the swap points used to calculate overnight interest swap charges for short positions on FX instruments.
     *
     * @return swap points for short positions
     */
    FixedPointNumber getShortSwapPoints();

    /**
     * Get the rate used for overnight funding.
     *
     * @return funding rate.
     * @deprecated this method always returns null as the funding rate applied varies in ways that a single rate figure cannot encapsulate.
     * @see #getFundingBaseRate()
     * @see #getFundingPremiumPercentage()
     * @see #getFundingReductionPercentage()
     */
    @Deprecated
    FixedPointNumber getFundingRate();

}
