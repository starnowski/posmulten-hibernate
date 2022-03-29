package com.github.starnowski.posmulten.hibernate.core.connections

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
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

            def connection = Mock(Connection)
            def statement = Mock(PreparedStatement)

            def context = Mock(ISharedSchemaContext)
            context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory() >> preparedStatement
            tested.injectServices(serviceRegistry)

        when:
            def result = tested.getConnection(tenantId)

        then:
            result == connection
            1 * connection.prepareStatement(preparedStatement) >> statement
            1 * statement.setString(1, tenantId)
            1 * statement.execute()

        where:
            tenantId        |   preparedStatement
            "7SDFA-IUDAF"   |   "some query"
            "some_id"       |   "proceduer(?)"
    }
}
