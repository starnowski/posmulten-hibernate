package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

/**
 * Interface for enricher component that enrich builder {@link DefaultSharedSchemaContextBuilder} based on metadata {@link Metadata}
 */
public interface IDefaultSharedSchemaContextBuilderMetadataEnricher {

    /**
     * The method enrich builder {@link DefaultSharedSchemaContextBuilder} based on metadata {@link Metadata}
     * @param builder builder object for enrich
     * @param metadata metadata object
     * @return builder object, it should be the same object passed as method parameter or its clone
     */
    DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata);

    /**
     * Check component was initialize
     * @return true when the component is initialize
     */
    boolean isInitialized();

    /**
     * Method that initialize component.
     * After invoking this method the return value by {@link #isInitialized()} should be impacted.
     * @param map hibernate configuration
     * @param serviceRegistryImplementor services register
     */
    void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor);
}
