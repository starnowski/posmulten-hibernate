package com.github.starnowski.posmulten.hibernate.hibernate5.connections

import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderProvider
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import com.github.starnowski.posmulten.postgresql.core.rls.function.ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Connection
import java.sql.PreparedStatement

class SharedSchemaMultiTenantConnectionProviderTest extends Specification {

    def tested = new SharedSchemaMultiTenantConnectionProvider()

    @Unroll
    def "should set string tenant id #tenantId for prepared statement #preparedStatement"()
    {
        given:
            def serviceRegistry = Mock(ServiceRegistryImplementor)
            def connectionProvider = Mock(ConnectionProvider)
            def defaultSharedSchemaContextBuilderProvider = Mock(IDefaultSharedSchemaContextBuilderProvider)
            def defaultSharedSchemaContextBuilder = Mock(DefaultSharedSchemaContextBuilder)
            def currentTenantPreparedStatementSetter = Mock(ICurrentTenantPreparedStatementSetter)
            defaultSharedSchemaContextBuilderProvider.get() >> defaultSharedSchemaContextBuilder

            def connection = Mock(Connection)
            connectionProvider.getConnection() >> connection

            def statement = Mock(PreparedStatement)
            def context = Mock(ISharedSchemaContext)
            def factory = Mock(ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory)
            defaultSharedSchemaContextBuilder.build() >> context
            context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory() >> factory
            factory.returnPreparedStatementThatSetCurrentTenant() >> preparedStatement

            serviceRegistry.getService(ConnectionProvider) >> connectionProvider
            serviceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider) >> defaultSharedSchemaContextBuilderProvider
            serviceRegistry.getService(ICurrentTenantPreparedStatementSetter) >> currentTenantPreparedStatementSetter
            tested.injectServices(serviceRegistry)

        when:
            def result = tested.getConnection(tenantId)

        then:
            result == connection
            1 * connection.prepareStatement(preparedStatement) >> statement
            1 * currentTenantPreparedStatementSetter.setup(statement, tenantId)
            1 * statement.execute()

        where:
            tenantId        |   preparedStatement
            "7SDFA-IUDAF"   |   "some query"
            "some_id"       |   "proceduer(?)"
    }

    def "should close connection by connectionProvider"()
    {
        given:
            def serviceRegistry = Mock(ServiceRegistryImplementor)
            def connectionProvider = Mock(ConnectionProvider)
            def defaultSharedSchemaContextBuilderProvider = Mock(IDefaultSharedSchemaContextBuilderProvider)
            def defaultSharedSchemaContextBuilder = Mock(DefaultSharedSchemaContextBuilder)
            def currentTenantPreparedStatementSetter = Mock(ICurrentTenantPreparedStatementSetter)
            defaultSharedSchemaContextBuilderProvider.get() >> defaultSharedSchemaContextBuilder

            def connection = Mock(Connection)
            connectionProvider.getConnection() >> connection

            def statement = Mock(PreparedStatement)
            def context = Mock(ISharedSchemaContext)
            def factory = Mock(ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory)
            defaultSharedSchemaContextBuilder.build() >> context
            context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory() >> factory
            factory.returnPreparedStatementThatSetCurrentTenant() >> preparedStatement

            serviceRegistry.getService(ConnectionProvider) >> connectionProvider
            serviceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider) >> defaultSharedSchemaContextBuilderProvider
            serviceRegistry.getService(ICurrentTenantPreparedStatementSetter) >> currentTenantPreparedStatementSetter
            tested.injectServices(serviceRegistry)

        when:
            tested.releaseAnyConnection(connection)

        then:
            1 * connectionProvider.closeConnection(connection)
    }
}
