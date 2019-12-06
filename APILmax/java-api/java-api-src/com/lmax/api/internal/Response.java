package com.lmax.api.internal;


import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Response
{
    public static final int HTTP_OK = 200;

    public enum Status
    {
        OK,
        FAIL
    }

    private final int httpStatusCode;
    private final String messagePayload;
    private final Map<String, List<String>> headers;

    public Response(final int httpStatusCode, final String messagePayload, final Map<String, List<String>> headers)
    {
        this.httpStatusCode = httpStatusCode;
        this.messagePayload = messagePayload;
        this.headers = headers;
    }

    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public String getMessagePayload()
    {
        return messagePayload;
    }

    public Status getStatus()
    {
        return httpStatusCode == HTTP_OK ? Status.OK : Status.FAIL;
    }

    public String toString()
    {
        return "Response{" +
               "httpStatusCode=" + httpStatusCode +
               ", messagePayload='" + messagePayload + '\'' +
               ", headers=" + headers +
               '}';
    }

    public List<String> getHeaderByName(final String headerName)
    {
        final String lowerCaseHeaderName = headerName.toLowerCase(Locale.ENGLISH);
        String key;
        for (final Map.Entry<String, List<String>> entry : headers.entrySet())
        {
            key = entry.getKey();
            if(key != null && key.toLowerCase(Locale.ENGLISH).equals(lowerCaseHeaderName))
            {
                return entry.getValue();
            }
        }
        return null;
    }
}
