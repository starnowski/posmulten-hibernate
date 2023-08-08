package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.enrichers;

import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers.CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers.ForeignKeySharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers.JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.common.Properties.METADATA_TABLE_ADDITIONAL_ENRICHERS;

public class DefaultSharedSchemaContextBuilderMetadataEnricher implements IDefaultSharedSchemaContextBuilderMetadataEnricher {

    private boolean initialized = false;

    private List<IDefaultSharedSchemaContextBuilderTableMetadataEnricher> enrichers;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
        DefaultSharedSchemaContextBuilder result = builder;
        Iterable<Namespace> namespaces = metadata.getDatabase().getNamespaces();
        for (Namespace namespace : namespaces) {
            for (IDefaultSharedSchemaContextBuilderTableMetadataEnricher enricher : enrichers) {
                for (Table table : namespace.getTables()) {
                    result = enricher.enrich(result, metadata, table);
                }
            }
        }
        return result;
    }

    public void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.enrichers = new ArrayList<>();
        enrichers.add(new RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher());
        enrichers.add(new JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher());
        enrichers.add(new ForeignKeySharedSchemaContextBuilderTableMetadataEnricher());
        enrichers.add(new CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher());
        if (map.containsKey(METADATA_TABLE_ADDITIONAL_ENRICHERS)) {
            String[] classNames = ((String) map.get(METADATA_TABLE_ADDITIONAL_ENRICHERS)).split(",");
            for (String className : classNames) {
                if (className.trim().isEmpty()) {
                    continue;
                }
                try {
                    enrichers.add(Class.forName(className).asSubclass(IDefaultSharedSchemaContextBuilderTableMetadataEnricher.class).getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        enrichers.forEach(enricher -> enricher.init(map, serviceRegistryImplementor));
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    List<IDefaultSharedSchemaContextBuilderTableMetadataEnricher> getEnrichers() {
        return enrichers;
    }

    void setEnrichers(List<IDefaultSharedSchemaContextBuilderTableMetadataEnricher> enrichers) {
        this.enrichers = enrichers;
    }
}
