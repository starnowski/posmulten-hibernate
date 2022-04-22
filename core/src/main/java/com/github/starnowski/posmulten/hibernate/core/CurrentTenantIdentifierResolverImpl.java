package com.github.starnowski.posmulten.hibernate.core;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.getCurrentTenant;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    public String resolveCurrentTenantIdentifier() {
        return getCurrentTenant();
    }

    public boolean validateExistingCurrentSessions() {
        //TODO Check usage
        return true;
    }
}
