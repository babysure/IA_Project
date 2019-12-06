package com.lmax.api.internal.xml;

/**
 * An exception used to signal an error when parsing a message.
 *
 * @author Martin Thompson
 */
public class XmlParseException
    extends RuntimeException
{
    private static final long serialVersionUID = 7517954413997397317L;

    /**
     * Construct the exception.
     * 
     * @param msg a description of the problem
     */
    public XmlParseException(final String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception.
     * 
     * @param msg a description of the problem
     * @param rootCause the underlying cause of the problem
     */
    public XmlParseException(final String msg, final Throwable rootCause)
    {
        super(msg, rootCause);
    }
}
