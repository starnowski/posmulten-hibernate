# Posmulten changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

https://keepachangelog.com/en/1.0.0/
https://www.markdownguide.org/basic-syntax/

* [Unreleased](#unreleased)
* [0.1.0](#010---2022-05-15)

## [Unreleased]


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