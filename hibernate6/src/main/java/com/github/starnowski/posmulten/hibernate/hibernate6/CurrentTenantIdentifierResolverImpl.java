package com.github.starnowski.posmulten.hibernate.hibernate6;


import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import static com.github.starnowski.posmulten.hibernate.common.context.CurrentTenantContext.getCurrentTenant;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    public String resolveCurrentTenantIdentifier() {
        return getCurrentTenant();
    }

    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
