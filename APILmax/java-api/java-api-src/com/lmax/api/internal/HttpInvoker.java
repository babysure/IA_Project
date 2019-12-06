package com.lmax.api.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import com.lmax.api.LmaxApiException;
import com.lmax.api.internal.xml.XmlStructuredWriter;

public class HttpInvoker
{
    private static final ThreadLocal<XmlStructuredWriter> WRITER = new ThreadLocal<XmlStructuredWriter>()
    {
        @Override
        protected XmlStructuredWriter initialValue()
        {
            return new XmlStructuredWriter();
        }
    };

    public Response doPost(final HttpURLConnection conn, final Request request)
    {
        InputStream inputStream = null;
        try
        {
            inputStream = openInputStream(conn, request);
            return readResponse(conn, inputStream);
        }
        catch (IOException e)
        {
            final String message = dealWithHttpException(conn);
            throw new LmaxApiException("There was a problem communicating with LMAX: " + message, e);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public Response doGet(final HttpURLConnection conn)
    {
        InputStream inputStream = null;
        try
        {
            conn.connect();
            inputStream = conn.getInputStream();
            return readResponse(conn, inputStream);
        }
        catch (IOException e)
        {
            final String message = dealWithHttpException(conn);
            throw new LmaxApiException("There was a problem communicating with LMAX: " + message, e);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public InputStream openInputStream(final HttpURLConnection connection, final Request request) throws IOException
    {
        connection.connect();

        //Send request
        sendRequest(connection, request);

        final int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK)
        {
            throw new UnexpectedHttpResponseCodeException(responseCode);
        }
        return connection.getInputStream();
    }

    public static Response readResponse(final HttpURLConnection conn, final InputStream inputStream)
    {
        try
        {
            final int httpStatusCode = conn.getResponseCode();
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024 * 16];

            int size;
            while (-1 != (size = inputStream.read(buffer)))
            {
                stream.write(buffer, 0, size);
            }
            final Map<String, List<String>> headerFields = conn.getHeaderFields();

            return new Response(httpStatusCode, new String(stream.toByteArray(), "UTF-8"), headerFields);
        }
        catch (IOException e)
        {
            throw new LmaxApiException("There was a problem communicating with LMAX", e);
        }
    }

    private void sendRequest(final HttpURLConnection conn, final Request request) throws IOException
    {
        final OutputStream out = conn.getOutputStream();

        final XmlStructuredWriter structuredWriter = WRITER.get();
        structuredWriter.reset();
        request.writeTo(structuredWriter);
        try
        {
            structuredWriter.writeTo(out);
            out.flush();
        }
        finally
        {
            out.close();
        }
    }

    private String dealWithHttpException(final HttpURLConnection conn)
    {
        final int responseCode;
        try
        {
            responseCode = conn.getResponseCode();
        }
        catch (final IOException e)
        {
            // We failed to deal with the HTTP error.
            return "Unknown Error";
        }

        InputStream errorStream = null;
        try
        {
            errorStream = conn.getErrorStream();
            final Response response = readResponse(conn, errorStream);
            return responseCode + ": " + response.getMessagePayload();
        }
        finally
        {
            closeQuietly(errorStream);
        }

    }

    public void closeQuietly(final InputStream inputStream)
    {
        if (inputStream != null)
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                //meh
            }
        }
    }
}
