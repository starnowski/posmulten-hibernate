package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

import com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProvider;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.rls.function.ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SharedSchemaMultiTenantConnectionProviderTest {

    private SharedSchemaMultiTenantConnectionProvider provider;
    private ConnectionProvider connectionProvider;
    private ISharedSchemaContext sharedSchemaContext;
    private ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory;

    @BeforeEach
    void setUp() {
        provider = new SharedSchemaMultiTenantConnectionProvider();
        connectionProvider = mock(ConnectionProvider.class);
        sharedSchemaContext = mock(ISharedSchemaContext.class);
        factory = mock(ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory.class);
        ServiceRegistryImplementor serviceRegistryImplementor = mock(ServiceRegistryImplementor.class);
        SharedSchemaContextProvider sscp = mock(SharedSchemaContextProvider.class);
        Mockito.when(serviceRegistryImplementor.getService(SharedSchemaContextProvider.class)).thenReturn(sscp);
        Mockito.when(serviceRegistryImplementor.getService(ConnectionProvider.class)).thenReturn(connectionProvider);
        Mockito.when(sscp.getSharedSchemaContext()).thenReturn(sharedSchemaContext);
        provider.injectServices(serviceRegistryImplementor);

        when(sharedSchemaContext.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory())
                .thenReturn(factory);
    }

    @Test
    void testGetAnyConnectionProvider() {
        // Invoke the method
        ConnectionProvider result = provider.getAnyConnectionProvider();

        // Perform assertions
        assertEquals(connectionProvider, result);
        // Add additional assertions based on the expected behavior of getAnyConnectionProvider()
    }

    @Test
    void testSelectConnectionProvider() {
        // Invoke the method
        ConnectionProvider result = provider.selectConnectionProvider("tenant");

        // Perform assertions
        assertEquals(connectionProvider, result);
        // Add additional assertions based on the expected behavior of selectConnectionProvider()
    }

    @Test
    void testGetAnyConnection() throws SQLException {
        Connection connection = mock(Connection.class);
        when(connectionProvider.getConnection()).thenReturn(connection);

        // Invoke the method
        Connection result = provider.getAnyConnection();

        // Perform assertions
        assertEquals(connection, result);
        // Add additional assertions based on the expected behavior of getAnyConnection()
    }

    @Test
    void testReleaseAnyConnection() throws SQLException {
        Connection connection = mock(Connection.class);

        // Invoke the method
        provider.releaseAnyConnection(connection);

        // Perform assertions or verify the behavior using Mockito
        verify(connectionProvider).closeConnection(connection);
    }

    @Test
    void testGetConnection() throws SQLException {
        String tenant = "tenant";
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connectionProvider.getConnection()).thenReturn(connection);
        when(factory.returnPreparedStatementThatSetCurrentTenant()).thenReturn("some SQL statement");
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        // Invoke the method
        Connection result = provider.getConnection(tenant);

        // Perform assertions or verify the behavior using Mockito
        assertEquals(connection, result);
        verify(statement).setString(1, tenant);
        verify(statement).execute();
    }

    @Test
    void testReleaseConnection() throws SQLException {
        String tenant = "tenant";
        Connection connection = mock(Connection.class);

        // Invoke the method
        provider.releaseConnection(tenant, connection);

        // Perform assertions or verify the behavior using Mockito
        verify(connectionProvider).closeConnection(connection);
    }

    @Test
    void testSupportsAggressiveRelease() {
        // Invoke the method
        boolean result = provider.supportsAggressiveRelease();

        // Perform assertions
        assertFalse(result);
        // Add additional assertions based on the expected behavior of supportsAggressiveRelease()
    }

    @Test
    void testIsUnwrappableAs() {
        // Invoke the method
        boolean result = provider.isUnwrappableAs(SomeClass.class);

        // Perform assertions
        assertFalse(result);
        // Add additional assertions based on the expected behavior of isUnwrappableAs()
    }

    @Test
    void testUnwrap() {
        // Invoke the method
        SomeClass result = provider.unwrap(SomeClass.class);

        // Perform assertions
        assertNull(result);
        // Add additional assertions based on the expected behavior of unwrap()
    }

    @Test
    void testInjectServices() {
        ServiceRegistryImplementor serviceRegistry = mock(ServiceRegistryImplementor.class);
        ConnectionProvider cp = mock(ConnectionProvider.class);
        SharedSchemaContextProvider sscp = mock(SharedSchemaContextProvider.class);
        ISharedSchemaContext context = mock(ISharedSchemaContext.class);
        when(serviceRegistry.getService(ConnectionProvider.class)).thenReturn(cp);
        when(serviceRegistry.getService(SharedSchemaContextProvider.class)).thenReturn(sscp);
        when(sscp.getSharedSchemaContext()).thenReturn(context);

        // Invoke the method
        provider.injectServices(serviceRegistry);

        // Perform assertions or verify the behavior using Mockito
        assertEquals(cp, provider.getConnectionProvider());
        assertEquals(context, provider.getContext());
    }

    private static class SomeClass {
        // Define a dummy class for testing
    }
}