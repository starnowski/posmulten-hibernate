package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertSame;

class Hibernate6ContextSupplierTest {

    @Test
    public void shouldReturnHibernateContextPassedAsConstructorArgument()
    {
        // GIVEN
        HibernateContext hibernateContext = Mockito.mock(HibernateContext.class);
        Hibernate6ContextSupplier hibernate6ContextSupplier = new Hibernate6ContextSupplier(hibernateContext);

        // WHEN
        HibernateContext result = hibernate6ContextSupplier.get();

        // THEN
        assertSame(result, hibernateContext);
    }
}