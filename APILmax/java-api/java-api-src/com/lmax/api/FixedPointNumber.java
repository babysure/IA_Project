package com.lmax.api;

import java.math.BigDecimal;

/**
 * FixedPointNumber class for use with the LMAX API.
 */
public final class FixedPointNumber
{
    static final long DECIMAL_PLACES = 6;
    static final long DECIMAL_RANGE = 1000000L;
    static final BigDecimal BIG_DECIMAL_RANGE = new BigDecimal(DECIMAL_RANGE);

    public static final FixedPointNumber ZERO = valueOf(0L);
    public static final FixedPointNumber ONE = valueOf(DECIMAL_RANGE);
    public static final FixedPointNumber TEN = valueOf(10L * DECIMAL_RANGE);

    private final long longValue;

    FixedPointNumber(final long value)
    {
        this.longValue = value;
    }

    /**
     * Create a new FixedPointNumber based on a long value.  This represents
     * a value out to six decimal places.  E.g. 12345678L represents
     * a price of 12.345678.
     *
     * @param value The value to be represented as a FixedPointNumber.
     * @return The new FixedPointNumber
     */
    public static FixedPointNumber valueOf(long value)
    {
        return new FixedPointNumber(value);
    }

    /**
     * Create a new FixedPointNumber based on a string representation of a
     * number, e.g. "12.345678" represents 12.345678
     *
     * @param value The value to be converted into a FixedPointNumber.
     * @return The new FixedPointNumber
     */
    public static FixedPointNumber valueOf(CharSequence value)
    {
        return new FixedPointNumber(stringAsLong(value));
    }

    /**
     * Create a new FixedPointNumber based on a string representation of a
     * number within a char[], e.g. "abc12.345678def" with an offset of 3
     * and count of 9 represents 12.345678
     *
     * @param content The char[] containing the value to be converted into a FixedPointNumber.
     * @param offset  The offset into content containing the start of the number.
     * @param count   The number of chars in content to use.
     * @return The new FixedPointNumber
     */
    public static FixedPointNumber valueOf(char[] content, int offset, int count)
    {
        return valueOf(String.valueOf(content, offset, count));
    }

    /**
     * Returns the signum function of this {@code FixedPointNumber}.
     *
     * @return -1, 0, or 1 as the value of this {@code FixedPointNumber}
     *         is negative, zero, or positive.
     */
    public long signum()
    {
        return Long.signum(longValue);
    }

    /**
     * Negate this FixedPointNumber.
     *
     * @return a new FixedPointNumber that is the negative of this number.
     */
    public FixedPointNumber negate()
    {
        return valueOf(-longValue);
    }

    /**
     * Convert this FixedPointNumber to a String removing trailing zeros.
     *
     * @return a String form for this FixedPointNumber
     */
    @Override
    public String toString()
    {
        final StringBuilder buffer = new StringBuilder();
        toStringBuilder(buffer);
        return buffer.toString();
    }

    /**
     * Populate the supplied CharSequence with the value of this FixedPointNumber.
     *
     * @param buffer a CharSequence to be populated with the value of this FixedPointNumber
     */
    public void toStringBuilder(final StringBuilder buffer)
    {
        toFullDepthString(buffer, longValue);
        stripTrailingZeros(buffer);
    }

    /**
     * <p>
     * Populate the supplied StringBuilder with the value of this FixedPointNumber, appending trailing
     * zeros up to the specified scale.
     * </p>
     * <p>
     * The scale MUST be greater than or equal to the scale of the FixedPointNumber value - no truncation
     * or rounding will occur, e.g. 12.345 with a scale of 4 will output "12.3450", but a scale of 2 will
     * cause an IllegalArgumentException to be thrown.
     * </p>
     *
     * @param buffer the StringBuilder to append the result to
     * @param scale  the scale to ensure the result is formatted to
     */
    public void format(final StringBuilder buffer, final int scale)
    {
        if (scale < 0 || scale > 6)
        {
            throw new IllegalArgumentException("Scale must be between 0-6 inclusive");
        }

        final long l = longValue / DECIMAL_RANGE;
        long fractional = Math.abs(longValue - (l * DECIMAL_RANGE));

        if (l == 0 && longValue < 0)
        {
            buffer.append('-');
        }
        buffer.append(l);
        final String fractionalString = Long.toString(truncateFractional(fractional, scale));

        if (fractional == 0L)
        {
            if (scale == 1)
            {
                buffer.append(".0");
            }
            else if (scale == 2)
            {
                buffer.append(".00");
            }
            else if (scale == 3)
            {
                buffer.append(".000");
            }
            else if (scale == 4)
            {
                buffer.append(".0000");
            }
            else if (scale == 5)
            {
                buffer.append(".00000");
            }
            else if (scale == 6)
            {
                buffer.append(".000000");
            }
        }
        else if (fractional > 99999L)
        {
            buffer.append(".");
            buffer.append(fractionalString);
        }
        else if (fractional > 9999L)
        {
            buffer.append(".0");
            buffer.append(fractionalString);
        }
        else if (fractional > 999L)
        {
            buffer.append(".00");
            buffer.append(fractionalString);
        }
        else if (fractional > 99L)
        {
            buffer.append(".000");
            buffer.append(fractionalString);
        }
        else if (fractional > 9L)
        {
            buffer.append(".0000");
            buffer.append(fractionalString);
        }
        else
        {
            buffer.append(".00000");
            buffer.append(fractional);
        }
    }

    /**
     * Convert this FixedPointNumber to a long value that represents a decimal
     * number with 6 decimal places.  A long value of <code>1</code> actually
     * represents a value of <code>0.000001</code>.
     *
     * @return a long form for this FixedPointNumber
     */
    public long longValue()
    {
        return longValue;
    }

    /**
     * Equality is based solely on the long value for this number.
     *
     * @param o the object to compare
     * @return true if this FixedPointNumber equals the argument
     */
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

        final FixedPointNumber that = (FixedPointNumber)o;

        return longValue == that.longValue;

    }

    /**
     * Hashcode is based solely on the long value for this number.
     *
     * @return the hashCode
     */
    @Override
    public int hashCode()
    {
        return (int)(longValue ^ (longValue >>> 32));
    }

    private void stripTrailingZeros(final StringBuilder s)
    {
        int index = indexOfLasSigDigit(s);
        s.setLength(index);
    }

    private int indexOfLasSigDigit(final CharSequence s)
    {
        for (int index = s.length(); index > 0; index--)
        {
            if (s.charAt(index - 1) == '.')
            {
                return index - 1;
            }
            if (s.charAt(index - 1) != '0')
            {
                return index;
            }
        }
        return s.length();
    }

    private void toFullDepthString(final StringBuilder buffer, final long longVal)
    {
        final long l = longVal / DECIMAL_RANGE;

        if (longValue < 0 && l == 0)
        {
            buffer.append('-');
        }
        buffer.append(l);

        addLeadingZeros(buffer, longVal - (l * DECIMAL_RANGE));
    }

    private static long stringAsLong(final CharSequence value)
    {
        final int dpAt = decimalPointAt(value);
        final long wholeNumberValue = Long.parseLong(value.subSequence(0, dpAt).toString());
        return ((Math.abs(wholeNumberValue) * DECIMAL_RANGE) + Long.parseLong(calculateDecimalValue(value, dpAt))) * (value.charAt(0) == '-' ? -1 : 1);
    }

    private static String calculateDecimalValue(final CharSequence value, final int dpAt)
    {
        final StringBuilder fractional = new StringBuilder(dpAt == value.length() ? "0" : value.subSequence(dpAt + 1, value.length()).toString());
        for (int index = fractional.length(); index < DECIMAL_PLACES; index++)
        {
            fractional.append('0');
        }
        return fractional.toString();
    }

    private static int decimalPointAt(final CharSequence value)
    {
        final int index = indexOf(value, '.');
        final int length = value.length();
        final int retVal = index == -1 ? length : index;

        if (length - retVal - 1 > DECIMAL_PLACES)
        {
            throw new NumberFormatException("FixedPointNumbers are limited to " + DECIMAL_PLACES + " decimal places.");
        }

        return retVal;
    }

    private static int indexOf(final CharSequence value, final char target)
    {
        for (int index = 0; index < value.length(); index++)
        {
            if (value.charAt(index) == target)
            {
                return index;
            }
        }

        return -1;
    }

    private void addLeadingZeros(final StringBuilder buffer, final long fractional)
    {
        final long l = Math.abs(fractional);
        final String longAsString = Long.toString(l);

        if (l == 0L)
        {
            buffer.append(".");
        }
        else if (l > 99999L)
        {
            buffer.append(".");
            buffer.append(longAsString);
        }
        else if (l > 9999L)
        {
            buffer.append(".0");
            buffer.append(longAsString);
        }
        else if (l > 999L)
        {
            buffer.append(".00");
            buffer.append(longAsString);
        }
        else if (l > 99L)
        {
            buffer.append(".000");
            buffer.append(longAsString);
        }
        else if (l > 9L)
        {
            buffer.append(".0000");
            buffer.append(longAsString);
        }
        else
        {
            buffer.append(".00000");
            buffer.append(l);
        }
    }

    private long truncateFractional(final long fractional, final int scale)
    {
        final long truncatedFractional;
        if (scale == 0)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 1000000, scale);
        }
        else if (scale == 1)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 100000, scale);
        }
        else if (scale == 2)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 10000, scale);
        }
        else if (scale == 3)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 1000, scale);
        }
        else if (scale == 4)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 100, scale);
        }
        else if (scale == 5)
        {
            truncatedFractional = truncateTrailingZeros(fractional, 10, scale);
        }
        else
        {
            truncatedFractional = fractional;
        }
        return truncatedFractional;
    }

    private long truncateTrailingZeros(final long fractional, int divisor, final int scale)
    {
        final long truncatedFractional;
        if (fractional % divisor != 0)
        {
            throw new IllegalArgumentException("Cannot format '" + longValue + " to scale " + scale + " - scale too small");
        }
        truncatedFractional = fractional / divisor;
        return truncatedFractional;
    }
}
