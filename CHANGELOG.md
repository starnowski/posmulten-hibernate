# Posmulten Hibernate changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

https://keepachangelog.com/en/1.0.0/
https://www.markdownguide.org/basic-syntax/

* [Unreleased](#unreleased)
* [0.2.1](#021---2023-07-26)
* [0.2.0](#020---2023-07-23)
* [0.1.1](#011---2022-06-16)
* [0.1.0](#010---2022-05-15)

## [Unreleased]

## [0.2.2] - 2023-08-08

### Fixed

- Closing connection by ConnectionProvider object in method com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaMultiTenantConnectionProvider#releaseAnyConnection(java.sql.Connection) [37](https://github.com/starnowski/posmulten-hibernate/issues/37)
- Closing connection by ConnectionProvider object in method com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaMultiTenantConnectionProvider#releaseConnection(java.lang.String, java.sql.Connection) [37](https://github.com/starnowski/posmulten-hibernate/issues/37)
- Closing connection by ConnectionProvider object in method com.github.starnowski.posmulten.hibernate.hibernate6.connection.SharedSchemaMultiTenantConnectionProvider#releaseAnyConnection(java.sql.Connection) [37](https://github.com/starnowski/posmulten-hibernate/issues/37)
- Closing connection by ConnectionProvider object in method com.github.starnowski.posmulten.hibernate.hibernate6.connection.SharedSchemaMultiTenantConnectionProvider#releaseConnection(java.lang.String, java.sql.Connection) [37](https://github.com/starnowski/posmulten-hibernate/issues/37)

## [0.2.1] - 2023-07-26

### Changed

- Fixed SharedSchemaConnectionProviderInitiatorAdapter, invoke DriverManagerConnectionProviderImpl#injectServices(ServiceRegistryImplementor) method [34](https://github.com/starnowski/posmulten-hibernate/issues/34)

## [0.2.0] - 2023-07-23

### Changed
- Hibernate 6 support [29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.TenantTable to package com.github.starnowski.posmulten.hibernate.common[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext to package com.github.starnowski.posmulten.hibernate.common.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTableProperties to package com.github.starnowski.posmulten.hibernate.common.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.util.NameUtils to package com.github.starnowski.posmulten.hibernate.hibernate5.util[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.CurrentTenantIdentifierResolverImpl to package com.github.starnowski.posmulten.hibernate.hibernate5[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.PosmultenIntegrator to package com.github.starnowski.posmulten.hibernate.hibernate5[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.Properties to package com.github.starnowski.posmulten.hibernate.hibernate5[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterForLong to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterForString to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.ICurrentTenantPreparedStatementSetter to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.SharedSchemaConnectionProviderInitiatorAdapter to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.connections.SharedSchemaMultiTenantConnectionProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.connections[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderProviderInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricherProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.LightweightDefaultSharedSchemaContextBuilderProvider to package com.github.starnowski.posmulten.hibernate.hibernate5.context[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContextInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.enrichers[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.CollectionResolver to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.RLSPolicyTableHelper to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TableUtils to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.ForeignKeySharedSchemaContextBuilderTableMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher to package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.PosmultenSchemaManagementTool to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorStrategyContext to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorStrategyContextInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorWrapper to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SharedSchemaContextSourceInput to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SourceDescriptorFactory to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.SourceDescriptorInitiator to package com.github.starnowski.posmulten.hibernate.hibernate5.schema[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy to package com.github.starnowski.posmulten.hibernate.hibernate5.schema.strategy[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
  - Move type com.github.starnowski.posmulten.hibernate.core.schema.strategy.MetadataSchemaCreatorStrategy to package com.github.starnowski.posmulten.hibernate.hibernate5.schema.strategy[29](https://github.com/starnowski/posmulten-hibernate/issues/29)

### Added
- Hibernate 6 support [29](https://github.com/starnowski/posmulten-hibernate/issues/29)
    - Added type com.github.starnowski.posmulten.hibernate.hibernate6.CurrentTenantIdentifierResolverImpl[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
    - Added type com.github.starnowski.posmulten.hibernate.hibernate6.connection.SharedSchemaConnectionProviderInitiatorAdapter[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
    - Added type com.github.starnowski.posmulten.hibernate.hibernate6.connection.SharedSchemaMultiTenantConnectionProvider[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
    - Added type com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProvider[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
    - Added type com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProviderInitiator[29](https://github.com/starnowski/posmulten-hibernate/issues/29)
 
## [0.1.1] - 2022-06-16
### Added
- Added method com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TableUtils#isAnyCollectionComponentIsTenantTable(org.hibernate.mapping.Collection, com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver, org.hibernate.mappingTable, org.hibernate.boot.Metadata)
    [23](https://github.com/starnowski/posmulten-hibernate/issues/23)
- Added method com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver#resolve(java.lang.Class, org.hibernate.mapping.Table, org.hibernate.boot.Metadata)
    [23](https://github.com/starnowski/posmulten-hibernate/issues/23)

### Fixed
- Create RLS policy for JoinTable only when at least on of reference table is tenant aware table
    [23](https://github.com/starnowski/posmulten-hibernate/issues/23)
  - Fixed method com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher#enrich(com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder, org.hibernate.boot.Metadata, org.hibernate.mapping.Table)
    [23](https://github.com/starnowski/posmulten-hibernate/issues/23)
  - Fixed extracting mapping of primary keys and its types in method com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver#resolve(org.hibernate.mapping.PersistentClass, org.hibernate.mapping.Table, org.hibernate.boot.Metadata)
    [23](https://github.com/starnowski/posmulten-hibernate/issues/23)

## [0.1.0] - 2022-05-15
### Added

- Add strategy component that sets tenant value for prepared parameter in 
    com.github.starnowski.posmulten.hibernate.core.connections.SharedSchemaMultiTenantConnectionProvider#getConnection(String tenant) 
    method [6](https://github.com/starnowski/posmulten-hibernate/issues/6)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterForLong
      [6](https://github.com/starnowski/posmulten-hibernate/issues/6)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterForString
      [6](https://github.com/starnowski/posmulten-hibernate/issues/6)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.CurrentTenantPreparedStatementSetterInitiator
      [6](https://github.com/starnowski/posmulten-hibernate/issues/6)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.ICurrentTenantPreparedStatementSetter
      [6](https://github.com/starnowski/posmulten-hibernate/issues/6)

- Create maven project [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.SharedSchemaConnectionProviderInitiatorAdapter
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.ForeignKeySharedSchemaContextBuilderTableMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.CollectionResolver
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTableProperties
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PosmultenUtilContext
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1) 
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PosmultenUtilContextInitiator
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProvider
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderProvider
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricherProvider
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.strategy.MetadataSchemaCreatorStrategy
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.PosmultenSchemaManagementTool
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorStrategyContext
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorStrategyContextInitiator
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorWrapper
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.schema.SharedSchemaContextSourceInput
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.CurrentTenantIdentifierResolverImpl
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.Properties
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)
    - Added component com.github.starnowski.posmulten.hibernate.core.TenantTable
      [1](https://github.com/starnowski/posmulten-hibernate/issues/1)

- Add implementation of MultiTenantConnectionProvider type [4](https://github.com/starnowski/posmulten-hibernate/issues/4)
    - Added component com.github.starnowski.posmulten.hibernate.core.connections.SharedSchemaMultiTenantConnectionProvider
      [4](https://github.com/starnowski/posmulten-hibernate/issues/4)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.RLSPolicyTableHelper
      [4](https://github.com/starnowski/posmulten-hibernate/issues/4)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TableUtils
      [4](https://github.com/starnowski/posmulten-hibernate/issues/4)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext
      [4](https://github.com/starnowski/posmulten-hibernate/issues/4)

- Added provider for DefaultSharedSchemaContextBuilder component without any redundant configuration 
  [7](https://github.com/starnowski/posmulten-hibernate/issues/7)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderProviderInitiator
      [7](https://github.com/starnowski/posmulten-hibernate/issues/7)
    - Added component com.github.starnowski.posmulten.hibernate.core.context.LightweightDefaultSharedSchemaContextBuilderProvider
      [7](https://github.com/starnowski/posmulten-hibernate/issues/7)