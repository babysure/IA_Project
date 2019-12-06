package com.lmax.api;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Generic failure response for outbound requests.
 */
public class FailureResponse 
{
    private final String message;
    private final String description;
    private final boolean isSystemFailure;
    private final Exception exception;
    
    private FailureResponse(boolean isSystemFailure, String message, String description, Exception exception)
    {
        this.isSystemFailure = isSystemFailure;
        this.message = message;
        this.description = description;
        this.exception = exception;
    }

    /**
     * Constructs a failure response.
     * 
     * @param message The failure message
     * @param description Readable description of the error
     */
    public FailureResponse(String message, String description)
    {
        this(false, message, description, null);
    }
    
    /**
     * Constructs a failure response.
     * 
     * @param isSystemFailure Indicates a system failure
     * @param message The failure message
     */
    public FailureResponse(boolean isSystemFailure, String message)
    {
        this(isSystemFailure, message, "", null);
    }

    /**
     * Constructs a failure response as a result of an
     * exception being caught.  Will always be a system
     * exception
     * 
     * @param e The captured exception
     */
    public FailureResponse(Exception e)
    {
        this(true, e.getMessage(), "", e);
    }

    /**
     * Get the failure message.
     * 
     * @return The failure message
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * More detailed description of the failure response, may not
     * be set depending on the nature of the failure.
     * 
     * @return Description of the failure, or "" if not set.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Indicates that the failure was system related.  This
     * could indicate a connection error for example.
     * 
     * @return If the failure indicates a system failure.
     */
    public boolean isSystemFailure()
    {
        return isSystemFailure;
    }
    
    /**
     * Returns an exception that may have occurred.
     * 
     * @return A captured exception, may be null.
     */
    public Exception getException()
    {
        return exception;
    }

    /**
     * Get the FailureResponse as a string.
     * 
     * @see java.lang.Object#toString()
     * 
     * @return The FailureResponse as a string
     */
    @Override
    public String toString()
    {
        final StringWriter exceptionStringWriter = new StringWriter();
        if (exception != null)
        {
            exception.printStackTrace(new PrintWriter(exceptionStringWriter));
        }

        return "FailureResponse [message=" + message + ", description=" + description + ", isSystemFailure=" + isSystemFailure + ", exception=" +
               exceptionStringWriter.toString() + "]";
    }
}
