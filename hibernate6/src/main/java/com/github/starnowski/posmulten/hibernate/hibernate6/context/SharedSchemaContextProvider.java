package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.service.Service;

public class SharedSchemaContextProvider implements Service {

    private final ISharedSchemaContext sharedSchemaContext;

    public SharedSchemaContextProvider(ISharedSchemaContext sharedSchemaContext) {
        this.sharedSchemaContext = sharedSchemaContext;
    }

    public ISharedSchemaContext getSharedSchemaContext()
    {
        return this.sharedSchemaContext;
    }
}
