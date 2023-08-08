package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.common.context.PropertiesHibernateContextFactory;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Hibernate6ContextSupplierInitiatorTest {

    @Mock
    private HibernateContext mockHibernateContext;

    @Mock
    private ServiceRegistryImplementor mockServiceRegistryImplementor;

    private Hibernate6ContextSupplierInitiator initiator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        initiator = new Hibernate6ContextSupplierInitiator(mockHibernateContext);
    }

    @Test
    public void testInitiateService() {
        // GIVEN
        Map<String, Object> map = new HashMap<>();

        // WHEN
        Hibernate6ContextSupplier result = initiator.initiateService(map, mockServiceRegistryImplementor);

        // THEN
        assertEquals(Hibernate6ContextSupplier.class, result.getClass());
        assertEquals(mockHibernateContext, result.get());
    }

    @Test
    public void testGetServiceInitiated() {
        // WHEN
        Class<Hibernate6ContextSupplier> serviceInitiated = initiator.getServiceInitiated();

        // THEN
        assertEquals(Hibernate6ContextSupplier.class, serviceInitiated);
    }

    @Test
    public void testShouldReturnHibernateContextCreatedBasedOnProperties()
    {
        // GIVEN
        Map<String, Object> map = new HashMap<>();
        HibernateContext hibernateContext = HibernateContext.builder().build();
        PropertiesHibernateContextFactory propertiesHibernateContextFactory = Mockito.mock(PropertiesHibernateContextFactory.class);
        initiator = new Hibernate6ContextSupplierInitiator(null, propertiesHibernateContextFactory);
        Mockito.when(propertiesHibernateContextFactory.build(map)).thenReturn(hibernateContext);

        // WHEN
        Hibernate6ContextSupplier result = initiator.initiateService(map, null);

        // THEN
        assertEquals(hibernateContext, result.get());
    }
}



