package com.lmax.api.account;


import com.lmax.api.LmaxApi;
import com.lmax.api.internal.Request;
import com.lmax.api.internal.xml.StructuredWriter;

/**
 * Encapsulates a request to login to LMAX.
 */
public class LoginRequest implements Request
{

    /**
     * The product type to use to connect to lmax.  Production
     * uses CFD_LIVE, which is the default.
     */
    public enum ProductType
    {
        CFD_LIVE,
        CFD_DEMO
    }

    private final String username;
    private final String password;
    private final ProductType productType;
    private final boolean checkProtocolVersion;

    /**
     * Constructs a request to login to LMAX.
     *
     * @param username The username
     * @param password The password
     * @param productType The product type (CFD_LIVE or CFD_DEMO)
     * @param checkProtocolVersion <tt>true</tt> to ensure that the protocol version used by the client and server are the same. Setting this to
     * <tt>false</tt> may cause errors or incorrect behaviour due to protocol changes.
     */
    public LoginRequest(final String username, final String password, final ProductType productType, final boolean checkProtocolVersion)
    {
        this.username = username;
        this.password = password;
        this.productType = productType;
        this.checkProtocolVersion = checkProtocolVersion;
    }

    /**
     * Constructs a request to login to LMAX.
     *
     * @param username    The username
     * @param password    The password
     * @param productType The product type (CFD_LIVE or CFD_DEMO)
     */
    public LoginRequest(final String username, final String password, final ProductType productType)
    {
        this(username, password, productType, true);
    }

    /**
     * Constructs a request to login to LMAX, will default the
     * product type to CFD_LIVE.
     *
     * @param username the username
     * @param password the password
     */
    public LoginRequest(final String username, final String password)
    {
        this(username, password, ProductType.CFD_LIVE);
    }

    /**
     * Returns the path to the login request.
     *
     * @return The path to the login request.
     */
    @Override
    public String getUri()
    {
        return "/public/security/login";
    }

    /**
     * Internal: Output this request.
     *
     * @param writer The destination for the content of this request
     */
    @Override
    public void writeTo(final StructuredWriter writer)
    {
        writer.startElement("req").
            startElement("body").
            valueUTF8("username", username).
            valueUTF8("password", password);
        if (includeProtocolVersion())
        {
            writer.value("protocolVersion", LmaxApi.PROTOCOL_VERSION);
        }
        writer.value("productType", productType.toString()).
            endElement("body").
            endElement("req");
    }

    private boolean includeProtocolVersion()
    {
        final String property = System.getProperty("com.lmax.api.unsafedonotvalidateprotocolversion");
        return checkProtocolVersion && (property == null || !property.trim().equalsIgnoreCase("true"));
    }
}
