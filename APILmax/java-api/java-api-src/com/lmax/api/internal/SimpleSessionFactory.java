package com.lmax.api.internal;

import com.lmax.api.Session;
import com.lmax.api.account.AccountDetails;


import static com.lmax.api.internal.ConnectionTimeouts.DEFAULT_CONNECTION_TIMEOUTS;
import static com.lmax.api.internal.CookieExtractor.extractCookie;

public class SimpleSessionFactory implements SessionFactory
{
    private final String urlBase;
    private final String clientIdentifier;
    private final ConnectionTimeouts connectionTimeouts;

    public SimpleSessionFactory(final String urlBase)
    {
        this(urlBase, null, DEFAULT_CONNECTION_TIMEOUTS);
    }

    public SimpleSessionFactory(final String urlBase, final String clientIdentifier)
    {
        this(urlBase, clientIdentifier, DEFAULT_CONNECTION_TIMEOUTS);
    }

    public SimpleSessionFactory(final String urlBase, final ConnectionTimeouts connectionTimeouts)
    {
        this(urlBase, null, connectionTimeouts);
    }

    public SimpleSessionFactory(final String urlBase, final String clientIdentifier, final ConnectionTimeouts connectionTimeouts)
    {
        this.urlBase = urlBase;
        this.clientIdentifier = clientIdentifier;
        this.connectionTimeouts = connectionTimeouts;
    }

    public Session createSession(final Response response, final AccountDetails accountDetails)
    {
        final String jSessionId = extractCookie(response);
        final ConnectionFactory connectionFactory = new ConnectionFactory(urlBase, clientIdentifier, jSessionId, connectionTimeouts);

        return new SessionImpl(connectionFactory, accountDetails, urlBase);
    }
}
