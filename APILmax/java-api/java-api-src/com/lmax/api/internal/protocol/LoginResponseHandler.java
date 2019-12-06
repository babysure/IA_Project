package com.lmax.api.internal.protocol;

import com.lmax.api.account.AccountDetails;

import org.xml.sax.SAXException;

public class LoginResponseHandler extends MapBasedHandler
{
    private static final String FALSE = "false";
    private static final String FUNDING_DISALLOWED = "fundingDisallowed";
    private static final String DISPLAY_LOCALE = "displayLocale";
    private static final String CURRENCY = "currency";
    private static final String FAILURE_TYPE = "failureType";
    private static final String REGISTRATION_LEGAL_ENTITY = "registrationLegalEntity";
    private static final String USERNAME = "username";
    private static final String ACCOUNT_ID = "accountId";

    private AccountDetails accountDetails;

    public LoginResponseHandler()
    {
        super(BODY);
        addHandler(STATUS);
        addHandler(ACCOUNT_ID);
        addHandler(USERNAME);
        addHandler(REGISTRATION_LEGAL_ENTITY);
        addHandler(MESSAGE);
        addHandler(FAILURE_TYPE);
        addHandler(CURRENCY);
        addHandler(DISPLAY_LOCALE);
        addHandler(FUNDING_DISALLOWED);
    }
    
    @Override
    public void endElement(String localName) throws SAXException
    {
        if (BODY.equals(localName))
        {
            if (OK.equalsIgnoreCase(getStringValue(STATUS)))
            {
                final long accountId = getLongValue(ACCOUNT_ID);
                final String username = getStringValue(USERNAME);
                final String registrationLegalEntity = getStringValue(REGISTRATION_LEGAL_ENTITY);
                final boolean fundingEnabled = FALSE.equalsIgnoreCase(getStringValue(FUNDING_DISALLOWED));
                final String currency = getStringValue(CURRENCY);
                final String displayLocale = getStringValue(DISPLAY_LOCALE);
                accountDetails = new AccountDetails(accountId, username, currency, registrationLegalEntity, displayLocale, fundingEnabled);
            }
        }
    }
    
    public boolean isOk()
    {
        return OK.equalsIgnoreCase(getStringValue(STATUS));
    }

    public String getMessage()
    {
        return getStringValue(MESSAGE);
    }

    public String getFailureType()
    {
        return getStringValue(FAILURE_TYPE);
    }

    public AccountDetails getAccountDetails()
    {
        return accountDetails;
    }
}
