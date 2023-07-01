package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.configuration.DefaultSharedSchemaContextBuilderFactoryResolver;
import com.github.starnowski.posmulten.configuration.NoDefaultSharedSchemaContextBuilderFactorySupplierException;
import com.github.starnowski.posmulten.configuration.core.context.IDefaultSharedSchemaContextBuilderFactory;
import com.github.starnowski.posmulten.configuration.core.exceptions.InvalidConfigurationException;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.context.decorator.DefaultDecoratorContext;
import com.github.starnowski.posmulten.postgresql.core.context.decorator.SharedSchemaContextDecoratorFactory;
import com.github.starnowski.posmulten.postgresql.core.context.exceptions.SharedSchemaContextBuilderException;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class SharedSchemaContextProviderInitiator implements StandardServiceInitiator<SharedSchemaContextProvider> {

    private final String configurationPath;
    private final DefaultDecoratorContext decoratorContext;

    public SharedSchemaContextProviderInitiator() {
        this(null, null);
    }
    public SharedSchemaContextProviderInitiator(String configurationPath, DefaultDecoratorContext decoratorContext) {
        this.configurationPath = configurationPath;
        this.decoratorContext = decoratorContext;
    }

    @Override
    public SharedSchemaContextProvider initiateService(Map<String, Object> map, ServiceRegistryImplementor serviceRegistryImplementor) {
        DefaultSharedSchemaContextBuilderFactoryResolver defaultSharedSchemaContextBuilderFactoryResolver = new DefaultSharedSchemaContextBuilderFactoryResolver();
        IDefaultSharedSchemaContextBuilderFactory factory = null;
        DefaultSharedSchemaContextBuilder builder = null;
        ISharedSchemaContext defaultContext = null;
        try {
            factory = defaultSharedSchemaContextBuilderFactoryResolver.resolve(configurationPath);
        } catch (NoDefaultSharedSchemaContextBuilderFactorySupplierException e) {
            throw new RuntimeException(e);
        }
        try {
            builder = factory.build(configurationPath);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            defaultContext = builder.build();
        } catch (SharedSchemaContextBuilderException e) {
            throw new RuntimeException(e);
        }
        SharedSchemaContextDecoratorFactory sharedSchemaContextDecoratorFactory = new SharedSchemaContextDecoratorFactory();
        return new SharedSchemaContextProvider(sharedSchemaContextDecoratorFactory.build(defaultContext, decoratorContext));
    }

    @Override
    public Class<SharedSchemaContextProvider> getServiceInitiated() {
        return SharedSchemaContextProvider.class;
    }
}


