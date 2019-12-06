package com.lmax.api.internal;

import java.io.IOException;

public class UnexpectedHttpResponseCodeException extends IOException
{
    private static final long serialVersionUID = 227429829L;
    private final int responseCode;

    public UnexpectedHttpResponseCodeException(final int responseCode)
    {
        super("Unexpected http response code: " + responseCode);
        this.responseCode = responseCode;
    }

    public int getResponseCode()
    {
        return responseCode;
    }
}
