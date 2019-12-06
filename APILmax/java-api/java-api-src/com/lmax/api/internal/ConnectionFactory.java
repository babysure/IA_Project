package com.lmax.api.internal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lmax.api.LmaxApi;


import static com.lmax.api.internal.ConnectionTimeouts.DEFAULT_CONNECTION_TIMEOUTS;

public class ConnectionFactory
{
    private final String urlBase;
    private final String sessionCookie;
    private final String userAgent;
    private final ConnectionTimeouts connectionTimeouts;

    public ConnectionFactory(final String urlBase)
    {
        this(urlBase, null, null, DEFAULT_CONNECTION_TIMEOUTS);
    }

    public ConnectionFactory(final String urlBase, final String clientIdentifier)
    {
        this(urlBase, clientIdentifier, null, DEFAULT_CONNECTION_TIMEOUTS);
    }

    public ConnectionFactory(final String urlBase, final String clientIdentifier, final String sessionCookie)
    {
        this(urlBase, clientIdentifier, sessionCookie, DEFAULT_CONNECTION_TIMEOUTS);
    }

    public ConnectionFactory(final String urlBase, final ConnectionTimeouts connectionTimeouts)
    {
        this(urlBase, null, null, connectionTimeouts);
    }

    public ConnectionFactory(final String urlBase, final String clientIdentifier, final ConnectionTimeouts connectionTimeouts)
    {
        this(urlBase, clientIdentifier, null, connectionTimeouts);
    }

    public ConnectionFactory(final String urlBase, final String clientIdentifier, final String sessionCookie, final ConnectionTimeouts connectionTimeouts)
    {
        this.urlBase = urlBase;
        this.sessionCookie = sessionCookie;
        this.userAgent = createUserAgent(clientIdentifier);
        this.connectionTimeouts = connectionTimeouts;
    }

    public HttpURLConnection createPostConnection(final Request request) throws IOException
    {
        final URL url = new URL(urlBase + request.getUri());
        final HttpURLConnection conn = createConnection(url);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "text/xml");
        conn.setRequestProperty("Content-Type", "text/xml; UTF-8");
        return conn;
    }

    public HttpURLConnection createGetConnection(final Request request) throws IOException
    {
        final URL url = new URL(urlBase + request.getUri());
        final HttpURLConnection conn = createConnection(url);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/xml");
        return conn;
    }

    public HttpURLConnection createConnection(final URL url) throws IOException
    {
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Accept", "*/*");
        conn.addRequestProperty("User-Agent", userAgent);
        if(connectionTimeouts != null)
        {
            conn.setConnectTimeout(connectionTimeouts.getConnectionTimeoutMs());
            conn.setReadTimeout(connectionTimeouts.getReadTimeoutMs());
        }

        if (sessionCookie != null)
        {
            conn.addRequestProperty("Cookie", sessionCookie);
        }

        return conn;
    }

    private String createUserAgent(final String connectionIdentifier)
    {
        final StringBuilder userAgentBuilder = new StringBuilder("LMAX Java API v");
        userAgentBuilder.append(LmaxApi.API_LIBRARY_VERSION);
        userAgentBuilder.append(" p");
        userAgentBuilder.append(LmaxApi.PROTOCOL_VERSION);
        userAgentBuilder.append(" jdk").append(System.getProperty("java.version"));
        if (connectionIdentifier != null && !connectionIdentifier.trim().equals(""))
        {
            userAgentBuilder.append(" ").append(connectionIdentifier.substring(0, Math.min(25, connectionIdentifier.length())));
        }
        return userAgentBuilder.toString();
    }
}
