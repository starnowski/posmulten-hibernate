package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import org.hibernate.service.Service;

import java.util.List;

public interface IDefaultSharedSchemaContextBuilderMetadataEnricherProvider extends Service {

    List<IDefaultSharedSchemaContextBuilderMetadataEnricher> getEnrichers();

    boolean isInitialized();
}
