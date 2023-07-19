package com.github.starnowski.posmulten.hibernate.hibernate6;

import com.github.starnowski.posmulten.hibernate.common.context.CurrentTenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrentTenantIdentifierResolverImplTest {

    private CurrentTenantIdentifierResolverImpl resolver;

    @BeforeEach
    void setUp() {
        resolver = new CurrentTenantIdentifierResolverImpl();
    }

    @Test
    void testShouldReturnCorrectCurrentTenantIdentifier() {
        // GIVEN
        String currentTenant = "test1";
        CurrentTenantContext.setCurrentTenant(currentTenant);

        // WHEN
        String result = resolver.resolveCurrentTenantIdentifier();

        // THEN
        assertEquals(result, currentTenant);
    }

    @Test
    void testValidateExistingCurrentSessionsShouldReturnTrue() {
        // WHEN
        boolean result = resolver.validateExistingCurrentSessions();

        // GIVEN
        assertTrue(result);
    }
}

