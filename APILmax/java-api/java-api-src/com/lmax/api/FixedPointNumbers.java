package com.lmax.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A utility class containing conversion methods to/from FixedPointNumber for a
 * variety of formats.
 */
public class FixedPointNumbers
{
    /**
     * Constructs a FixedPointNumber from a long, where a long represents a
     * number with 6 decimal places.  This method will also round the number
     * to the specified increment using a half even rounding mode.  This is a 
     * convenince to help prevent invalid prices/quantities being sent to 
     * the server.  The increment values for price and quantity can be obtained 
     * from the Instrument OrderBook information.
     * 
     * @param value A long representing 6 decimal places
     * @param increment The specified increment to use in rounding
     * @return A new FixedPointNumber
     * @see FixedPointNumber
     * @see RoundingMode#HALF_EVEN
     * @see com.lmax.api.orderbook.Instrument#getOrderBook()
     */
    public static FixedPointNumber toFixedPointNumber(long value, FixedPointNumber increment)
    {
        final long roundedResult = roundHalfEven(value, increment.longValue());
        return FixedPointNumber.valueOf(roundedResult);
    }

    /**
     * Constructs a FixedPointNumber from a double. This method will also round the number
     * to the specified increment using a half even rounding mode.  This is a 
     * convenince to help prevent invalid prices/quantities being sent to 
     * the server.  The increment values for price and quantity can be obtained 
     * from the Instrument OrderBook information.
     * 
     * @param value Value to be used
     * @param increment The specified increment to use in rounding
     * @return A new FixedPointNumber
     * @see FixedPointNumber
     * @see RoundingMode#HALF_EVEN
     * @see com.lmax.api.orderbook.Instrument#getOrderBook()
     */
    public static FixedPointNumber toFixedPointNumber(double value, FixedPointNumber increment)
    {
        final long valueAsLong = (long) (value * 1000000D);
        final long roundedResult = roundHalfEven(valueAsLong, increment.longValue());
        return FixedPointNumber.valueOf(roundedResult);
    }

    /**
     * Constructs a FixedPointNumber from a BigDecimal.  This method will also round the number
     * to the specified increment using a half even rounding mode.  This is a 
     * convenince to help prevent invalid prices/quantities being sent to 
     * the server.  The increment values for price and quantity can be obtained 
     * from the Instrument OrderBook information.
     * 
     * @param value Value to be used
     * @param increment The specified increment to use in rounding
     * @return A new FixedPointNumber
     * @see FixedPointNumber
     * @see RoundingMode#HALF_EVEN
     * @see com.lmax.api.orderbook.Instrument#getOrderBook()
     */
    public static FixedPointNumber toFixedPointNumber(BigDecimal value, FixedPointNumber increment)
    {
        final long valueAsLong = value.multiply(FixedPointNumber.BIG_DECIMAL_RANGE).setScale(0, RoundingMode.HALF_EVEN).longValue();
        final long roundedResult = roundHalfEven(valueAsLong, increment.longValue());
        return FixedPointNumber.valueOf(roundedResult);        
    }

    /**
     * Get a double value from a FixedPointNumber.
     * 
     * @param value The value to convert
     * @return The double result
     */
    public static double doubleValue(FixedPointNumber value)
    {
        return value.longValue() / (double) FixedPointNumber.DECIMAL_RANGE;
    }

    /**
     * Convert a FixedPointNumber to a big decimal.
     * 
     * @param value The value to convert
     * @return The BigDecimal result
     */
    public static BigDecimal bigDecimalValue(FixedPointNumber value)
    {
        return new BigDecimal(value.longValue()).movePointLeft((int) FixedPointNumber.DECIMAL_PLACES);
    }

    private static long roundHalfEven(final long value, final long divisor)
    {
        int signum = Long.signum(value);
        long absValue = value * signum;
        
        long remainder = absValue % divisor;
        long quotient = absValue / divisor;
        long halfDivisor = divisor >> 1;
        if (remainder > halfDivisor || (remainder == halfDivisor && (quotient & 1) == 1))
        {
            quotient++;
        }
        final long roundedResult = quotient * divisor;
        return roundedResult * signum;
    }
}
