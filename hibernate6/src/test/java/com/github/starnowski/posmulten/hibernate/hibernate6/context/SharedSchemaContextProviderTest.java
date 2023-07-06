package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SharedSchemaContextProviderTest {

    private SharedSchemaContextProvider provider;
    private ISharedSchemaContext sharedSchemaContext;

    @BeforeEach
    void setUp() {
        sharedSchemaContext = mock(ISharedSchemaContext.class);
        provider = new SharedSchemaContextProvider(sharedSchemaContext);
    }

    @Test
    void testGetSharedSchemaContext() {
        // Invoke the method
        ISharedSchemaContext result = provider.getSharedSchemaContext();

        // Perform assertions
        assertEquals(sharedSchemaContext, result);
        // Add additional assertions based on the expected behavior of getSharedSchemaContext()
    }
}