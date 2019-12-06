package com.lmax.api.account;

/**
 * Contains the account details for the logged-in account.
 */
public class AccountDetails
{
    private final String username;
    private final String currency;
    private final long accountId;
    private final String registrationLegalEntity;
    private final String displayLocale;
    private final boolean fundingAllowed;

    /**
     * Constructor for the AccountDetails value object.
     *
     * @param accountId               the account id for the registered user.
     * @param username                the username for the registered user.
     * @param currency                the base currency for the registered user.
     * @param registrationLegalEntity the legal entity the user has registered in.
     * @param displayLocale           the locale the user has registered in.
     * @param fundingAllowed          true if funding is allowed.
     */
    public AccountDetails(final long accountId,
                          final String username,
                          final String currency,
                          final String registrationLegalEntity,
                          final String displayLocale,
                          final boolean fundingAllowed)
    {
        this.accountId = accountId;
        this.username = username;
        this.currency = currency;
        this.registrationLegalEntity = registrationLegalEntity;
        this.displayLocale = displayLocale;
        this.fundingAllowed = fundingAllowed;
    }
    
    /**
     * Returns the username for the registered user.
     * @return the username for the registered user.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the base currency for the registered user.
     *
     * @return the base currency for the registered user.
     */
    public String getCurrency()
    {
        return currency;
    }

    /**
     * Returns the account id for the registered user.
     *
     * @return the account id for the registered user.
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * Returns the legal entity the user has registered in.
     *
     * @return the legal entity the user has registered in.
     */
    public String getRegistrationLegalEntity()
    {
        return registrationLegalEntity;
    }

    /**
     * Returns the locale the user has registered in.
     *
     * @return the locale the user has registered in.
     */
    public String getDisplayLocale()
    {
        return displayLocale;
    }

    /**
     * Returns true if funding is allowed.
     *
     * @return true if funding is allowed.
     */
    public boolean isFundingAllowed()
    {
        return fundingAllowed;
    }

    /**
     * String representation of AccountDetails.
     * 
     * @return AccountDetails as a string
     */
    @Override
    public String toString()
    {
        return "AccountDetails [accountId=" + accountId + ", username=" + username + ", currency=" + currency + ", registrationLegalEntity=" +
               registrationLegalEntity + ", displayLocale=" + displayLocale + ", fundingAllowed=" + fundingAllowed + "]";
    }
}
