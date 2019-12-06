package com.lmax.api.internal;

public class ConnectionTimeouts
{
    public static final ConnectionTimeouts DEFAULT_CONNECTION_TIMEOUTS = new ConnectionTimeouts(10000, 10000);
    public static final ConnectionTimeouts NEVER_TIMEOUT_CONNECTION_TIMEOUTS = new ConnectionTimeouts(-1, -1);

    private final int connectionTimeoutMs;
    private final int readTimeoutMs;

    public ConnectionTimeouts(final int connectionTimeoutMs, final int readTimeoutMs)
    {

        this.connectionTimeoutMs = connectionTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    public int getReadTimeoutMs()
    {
        return readTimeoutMs;
    }

    public int getConnectionTimeoutMs()
    {
        return connectionTimeoutMs;
    }
}
