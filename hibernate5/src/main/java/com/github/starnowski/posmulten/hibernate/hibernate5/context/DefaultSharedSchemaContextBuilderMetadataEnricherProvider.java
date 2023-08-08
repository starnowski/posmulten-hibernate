package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.common.Properties.METADATA_ADDITIONAL_ENRICHERS;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProvider implements IDefaultSharedSchemaContextBuilderMetadataEnricherProvider {

    private List<IDefaultSharedSchemaContextBuilderMetadataEnricher> enrichers = null;
    private boolean initialized = false;

    public DefaultSharedSchemaContextBuilderMetadataEnricherProvider() {
    }

    public void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.enrichers = new ArrayList<>();
        this.enrichers.add(new DefaultSharedSchemaContextBuilderMetadataEnricher());
        if (map.containsKey(METADATA_ADDITIONAL_ENRICHERS)) {
            String[] classNames = ((String) map.get(METADATA_ADDITIONAL_ENRICHERS)).split(",");
            for (String className : classNames) {
                if (className.trim().isEmpty()) {
                    continue;
                }
                try {
                    enrichers.add(Class.forName(className).asSubclass(IDefaultSharedSchemaContextBuilderMetadataEnricher.class).getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        enrichers.forEach(enricher -> enricher.initiateService(map, serviceRegistryImplementor));
        this.initialized = true;
    }

    public List<IDefaultSharedSchemaContextBuilderMetadataEnricher> getEnrichers() {
        return enrichers;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
