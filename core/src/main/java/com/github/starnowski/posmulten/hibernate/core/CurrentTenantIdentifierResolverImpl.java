package com.github.starnowski.posmulten.hibernate.core;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    public String resolveCurrentTenantIdentifier() {
        return null;
    }

    public boolean validateExistingCurrentSessions() {
        //TODO Check usage
        return true;
    }
}
