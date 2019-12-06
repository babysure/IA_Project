package com.lmax.api;

/**
 * Thrown whenever there is a problem in the API.
 */
public class LmaxApiException extends RuntimeException
{
    private static final long serialVersionUID = 227429828L;

    /**
     * Constructs a new LMAX Api exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public LmaxApiException(final String message)
    {
        super(message);
    }

    /**
     * Constructs a new LMAX Api exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public LmaxApiException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
