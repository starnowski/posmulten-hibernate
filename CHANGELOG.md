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


