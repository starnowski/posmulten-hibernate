package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.configuration.DefaultSharedSchemaContextBuilderFactoryResolver;
import com.github.starnowski.posmulten.configuration.NoDefaultSharedSchemaContextBuilderFactorySupplierException;
import com.github.starnowski.posmulten.configuration.core.context.IDefaultSharedSchemaContextBuilderFactory;
import com.github.starnowski.posmulten.configuration.core.exceptions.InvalidConfigurationException;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.decorator.DefaultDecoratorContext;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SharedSchemaContextProviderInitiatorTest {

    private SharedSchemaContextProviderInitiator tested;
    private ServiceRegistryImplementor serviceRegistryImplementor;

    @BeforeEach
    void setUp() {
        serviceRegistryImplementor = mock(ServiceRegistryImplementor.class);
    }

    @Test
    void testInitiateService() throws NoDefaultSharedSchemaContextBuilderFactorySupplierException, InvalidConfigurationException {
        // GIVEN
        // Prepare test data
        String configurationPath = "path/to/configuration";
        DefaultDecoratorContext decoratorContext = new DefaultDecoratorContext(new HashMap<>());

        // Set up the map
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        DefaultSharedSchemaContextBuilderFactoryResolver defaultSharedSchemaContextBuilderFactoryResolver = mock(DefaultSharedSchemaContextBuilderFactoryResolver.class);
        IDefaultSharedSchemaContextBuilderFactory iDefaultSharedSchemaContextBuilderFactory = mock(IDefaultSharedSchemaContextBuilderFactory.class);
        DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = mock(DefaultSharedSchemaContextBuilder.class);
        when(defaultSharedSchemaContextBuilderFactoryResolver.resolve(configurationPath)).thenReturn(iDefaultSharedSchemaContextBuilderFactory);
        when(iDefaultSharedSchemaContextBuilderFactory.build(configurationPath)).thenReturn(defaultSharedSchemaContextBuilder);

        tested = new SharedSchemaContextProviderInitiator(configurationPath, decoratorContext, defaultSharedSchemaContextBuilderFactoryResolver);

        // WHEN
        SharedSchemaContextProvider result = tested.initiateService(map, serviceRegistryImplementor);

        // THEN
        assertNotNull(result);
        // Add additional assertions based on the expected behavior of the method
    }

    @Test
    void testGetServiceInitiated() {
        // GIVEN
        tested = new SharedSchemaContextProviderInitiator();

        // WHEN
        Class<SharedSchemaContextProvider> result = tested.getServiceInitiated();

        // THEN
        assertNotNull(result);
        assertEquals(SharedSchemaContextProvider.class, result);
    }

    @Test
    void testShouldReturnCorrectImplementationForDefaultSharedSchemaContextBuilderFactoryResolverProperty()
    {
        // GIVEN
        tested = new SharedSchemaContextProviderInitiator();

        // WHEN
        DefaultSharedSchemaContextBuilderFactoryResolver result = tested.getDefaultSharedSchemaContextBuilderFactoryResolver();

        // THEN
        assertNotNull(result);
        assertEquals(DefaultSharedSchemaContextBuilderFactoryResolver.class, result.getClass());
    }
}