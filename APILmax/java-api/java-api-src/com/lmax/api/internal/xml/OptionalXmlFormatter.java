package com.lmax.api.internal.xml;


import com.lmax.api.FixedPointNumber;

public class OptionalXmlFormatter
{
    public static void toXmlWhereZeroValueMeansNotRequired(final StringBuilder buffer, final String tagName, final long tagValue)
    {
        if (tagValue != 0)
        {
            buffer.append('<').append(tagName).append('>');
            buffer.append(tagValue);
            buffer.append("</").append(tagName).append('>');

        }
    }

    public static void toXmlWhereNullValueMeansNotRequired(final StringBuilder buffer, final String tagName, final FixedPointNumber tagValue)
    {
        if (tagValue != null)
        {
            createElement(buffer, tagName, tagValue);
        }
    }

    public static void toXmlWhereNullValueMeansEmptyTag(final StringBuilder buffer, final String tagName, final FixedPointNumber tagValue)
    {
        if (tagValue == null)
        {
            buffer.append('<').append(tagName).append("/>");
        }
        else
        {
            createElement(buffer, tagName, tagValue);
        }
    }

    private static void createElement(final StringBuilder buffer, final String tagName, final FixedPointNumber tagValue)
    {
        buffer.append('<').append(tagName).append('>');
        tagValue.toStringBuilder(buffer);
        buffer.append("</").append(tagName).append('>');
    }
}
