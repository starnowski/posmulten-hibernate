package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SharedSchemaConnectionProviderInitiatorAdapterTest {
    private SharedSchemaConnectionProviderInitiatorAdapter initiator;
    private DriverManagerConnectionProviderImpl driverManagerConnectionProvider;

    @BeforeEach
    public void setUp() {
        driverManagerConnectionProvider = Mockito.mock(DriverManagerConnectionProviderImpl.class);
        Supplier<DriverManagerConnectionProviderImpl> driverManagerConnectionProviderSupplier = () -> driverManagerConnectionProvider;
        initiator = new SharedSchemaConnectionProviderInitiatorAdapter(driverManagerConnectionProviderSupplier);
    }

    @Test
    public void testGetServiceInitiated() {
        Class<ConnectionProvider> expectedClass = ConnectionProvider.class;
        Class<ConnectionProvider> actualClass = initiator.getServiceInitiated();
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void testInitiateService() {
        Map<String, Object> configurationValues = new HashMap<>();
        configurationValues.put("key1", "value1");
        configurationValues.put("key2", "value2");
        ServiceRegistryImplementor registry = Mockito.mock(ServiceRegistryImplementor.class);

        ConnectionProvider result = initiator.initiateService(configurationValues, registry);

        assertEquals(DriverManagerConnectionProviderImpl.class, result.getClass());
        verify(driverManagerConnectionProvider, times(1)).configure(configurationValues);
        verify(driverManagerConnectionProvider, times(1)).injectServices(registry);
    }
}
