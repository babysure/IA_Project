package com.lmax.api.internal;

import java.util.List;

public final class CookieExtractor
{
    private static final String DELIMITER = "; ";

    public static String extractCookie(final Response response)
    {
        final List<String> cookies = response.getHeaderByName("Set-Cookie");

        if (cookies == null)
        {
            return null;
        }

        final StringBuilder builder = new StringBuilder();

        for (final String cookie : cookies)
        {
            builder.append(extractCookiePair(cookie));
            builder.append(DELIMITER);
        }
        builder.delete(builder.length() - DELIMITER.length(), builder.length());

        return builder.toString();
    }

    private static String extractCookiePair(final String cookie)
    {
        final int index = cookie.indexOf(';');
        return (index != -1) ? cookie.substring(0, index) : cookie;
    }
}
