# Posmulten Hibernate changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

https://keepachangelog.com/en/1.0.0/
https://www.markdownguide.org/basic-syntax/

* [Unreleased](#unreleased)
* [0.1.1](#011---2022-06-16)
* [0.1.0](#010---2022-05-15)

## [Unreleased]

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