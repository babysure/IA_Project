package com.lmax.api.internal;

import com.lmax.api.Session;
import com.lmax.api.account.AccountDetails;

public interface SessionFactory
{
    Session createSession(Response loginResponse, AccountDetails accountDetails);
}
