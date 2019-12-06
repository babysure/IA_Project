package com.lmax.api;

import java.io.StringReader;
import java.net.HttpURLConnection;

import com.lmax.api.account.LoginCallback;
import com.lmax.api.account.LoginRequest;
import com.lmax.api.internal.ConnectionFactory;
import com.lmax.api.internal.ConnectionTimeouts;
import com.lmax.api.internal.DefaultXmlParser;
import com.lmax.api.internal.HttpInvoker;
import com.lmax.api.internal.Response;
import com.lmax.api.internal.SessionFactory;
import com.lmax.api.internal.SimpleSessionFactory;
import com.lmax.api.internal.XmlParser;
import com.lmax.api.internal.protocol.LoginResponseHandler;
import com.lmax.api.internal.protocol.SaxParserHelper;

import org.xml.sax.InputSource;

/**
 * <p>
 * The root object for accessing the LMAX API.
 * </p>
 * <p>
 * For full information on how to use the LMAX API, see <a href="doc-files/Tutorial.html">the Tutorial</a>.
 * </p>
 */

public class LmaxApi
{
    public static final String PROTOCOL_VERSION = "1.8";
    public static final String API_LIBRARY_VERSION = "1.9.3";

    private final SessionFactory sessionFactory;
    private final ConnectionFactory newSessionConnectionFactory;
    private final HttpInvoker httpInvoker;
    private final XmlParser saxParser;

    /**
     * Constructs an LMAX Api entry point with default options.
     */
    public LmaxApi()
    {
        this("https://api.lmaxtrader.com");
    }

    /**
     * Constructs an LMAX Api entry point to connect to the specified url.
     *
     * @param urlBase The protocol hostname and port specification for the connection
     */
    public LmaxApi(final String urlBase)
    {
        this(new ConnectionFactory(urlBase),
             new SimpleSessionFactory(urlBase),
             new HttpInvoker(),
             new DefaultXmlParser());
    }

    /**
     * Constructs an LMAX Api entry point to connect to the specified url.
     *
     * @param urlBase The protocol hostname and port specification for the connection
     * @param connectionTimeouts the timeouts to be used when establishing connections to LMAX
     */
    public LmaxApi(final String urlBase, final ConnectionTimeouts connectionTimeouts)
    {
        this(new ConnectionFactory(urlBase, connectionTimeouts),
             new SimpleSessionFactory(urlBase),
             new HttpInvoker(),
             new DefaultXmlParser());
    }

    /**
     * Constructs an LMAX Api entry point to connect to the specified url.
     *
     * @param urlBase The protocol hostname and port specification for the connection
     * @param clientIdentifier Identifies the client in HTTP requests
     */
    public LmaxApi(final String urlBase, final String clientIdentifier)
    {
        this(new ConnectionFactory(urlBase, clientIdentifier),
             new SimpleSessionFactory(urlBase, clientIdentifier),
             new HttpInvoker(),
             new DefaultXmlParser());
    }

    /**
     * Constructs an LMAX Api entry point to connect to the specified url.
     *
     * @param urlBase The protocol hostname and port specification for the connection
     * @param clientIdentifier Identifies the client in HTTP requests
     * @param connectionTimeouts the timeouts to be used when establishing connections to LMAX
     */
    public LmaxApi(final String urlBase, final String clientIdentifier, final ConnectionTimeouts connectionTimeouts)
    {
        this(new ConnectionFactory(urlBase, clientIdentifier, connectionTimeouts),
             new SimpleSessionFactory(urlBase, clientIdentifier),
             new HttpInvoker(),
             new DefaultXmlParser());
    }


    // @ExposedForTesting
    protected LmaxApi(final ConnectionFactory connectionFactory,
                      final SessionFactory sessionFactory,
                      final HttpInvoker httpInvoker,
                      final XmlParser saxParser)
    {
        this.newSessionConnectionFactory = connectionFactory;
        this.sessionFactory = sessionFactory;
        this.httpInvoker = httpInvoker;
        this.saxParser = saxParser;
    }

    /**
     * Logs into the LMAX trading system using the specified login details.
     * Will notify the listener on success or failure.
     *
     * @param message  a message containing the required login details
     * @param callback the object to notify of login success/failure
     */
    public void login(final LoginRequest message, final LoginCallback callback)
    {
        final Response response;
        LoginResponseHandler handler = new LoginResponseHandler();

        try
        {
            final HttpURLConnection conn = newSessionConnectionFactory.createPostConnection(message);
            response = httpInvoker.doPost(conn, message);

            if (response.getStatus() != Response.Status.OK)
            {
                callback.onLoginFailure(new FailureResponse(true, "HttpStatus: " + response.getHttpStatusCode()));
                return;
            }

            saxParser.parse(new InputSource(new StringReader(response.getMessagePayload())), new SaxParserHelper(handler));

            if (!handler.isOk())
            {
                callback.onLoginFailure(new FailureResponse(handler.getFailureType(), handler.getMessage()));
                return;
            }
        }
        catch (Exception e)
        {
            callback.onLoginFailure(new FailureResponse(e));
            return;
        }

        callback.onLoginSuccess(sessionFactory.createSession(response, handler.getAccountDetails()));
    }
}
