package com.github.starnowski.posmulten.hibernate.core.context;

import org.hibernate.service.Service;

import java.util.List;

public interface IDefaultSharedSchemaContextBuilderMetadataEnricherProvider extends Service {

    List<IDefaultSharedSchemaContextBuilderMetadataEnricher> getEnrichers();

    boolean isInitialized();
}
