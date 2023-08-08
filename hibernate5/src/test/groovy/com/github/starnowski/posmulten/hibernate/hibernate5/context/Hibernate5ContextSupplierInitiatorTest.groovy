package com.github.starnowski.posmulten.hibernate.hibernate5.context

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext
import com.github.starnowski.posmulten.hibernate.common.context.PropertiesHibernateContextFactory
import org.hibernate.service.spi.ServiceRegistryImplementor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

class Hibernate5ContextSupplierInitiatorSpec extends Specification {

    @Mock
    private HibernateContext hibernateContext

    @Mock
    private ServiceRegistryImplementor serviceRegistryImplementor

    def setup() {
        MockitoAnnotations.openMocks(this)
    }

    def "test initiateService"() {
        given:
            Hibernate5ContextSupplierInitiator tested = new Hibernate5ContextSupplierInitiator(hibernateContext)
            Map map = new HashMap()

        when:
            Hibernate5ContextSupplier result = tested.initiateService(map, serviceRegistryImplementor)

        then:
            result instanceof Hibernate5ContextSupplier
            result.get() == hibernateContext
    }

    def "test getServiceInitiated"() {
        given:
            Hibernate5ContextSupplierInitiator tested = new Hibernate5ContextSupplierInitiator(hibernateContext)

        when:
            Class<Hibernate5ContextSupplier> serviceInitiated = tested.getServiceInitiated()

        then:
            serviceInitiated == Hibernate5ContextSupplier
    }

    def "test should return hibernate context created based on properties"()
    {
        given:
            Map<String, Object> map = new HashMap<>()
            HibernateContext hibernateContext = HibernateContext.builder().build()
            PropertiesHibernateContextFactory propertiesHibernateContextFactory = Mock(PropertiesHibernateContextFactory)
            Hibernate5ContextSupplierInitiator tested = new Hibernate5ContextSupplierInitiator(null, propertiesHibernateContextFactory)

        when:
            Hibernate5ContextSupplier result = tested.initiateService(map, null)

        then:
            1 * propertiesHibernateContextFactory.build(map) >> hibernateContext
            hibernateContext == result.get()
    }
}