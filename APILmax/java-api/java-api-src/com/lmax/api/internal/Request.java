package com.lmax.api.internal;

import com.lmax.api.internal.xml.StructuredWriter;

public interface Request
{
    String getUri();

    void writeTo(StructuredWriter writer);
}
