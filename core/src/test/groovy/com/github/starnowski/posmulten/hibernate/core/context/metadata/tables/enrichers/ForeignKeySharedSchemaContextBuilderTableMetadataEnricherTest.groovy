package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.core.TenantTable
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.mapping.ForeignKey
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Unroll

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

class ForeignKeySharedSchemaContextBuilderTableMetadataEnricherTest extends AbstractDefaultSharedSchemaContextBuilderTableMetadataEnricherTest<ForeignKeySharedSchemaContextBuilderTableMetadataEnricher> {

    ForeignKeySharedSchemaContextBuilderTableMetadataEnricher tested = new ForeignKeySharedSchemaContextBuilderTableMetadataEnricher()

    @Unroll
    def "should enrich builder for foreign keys that has reference to tenant table, configuration #configuration"()
    {
        given:
            def foreignKeyWithTenantTable1 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithTenantTable2 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithoutTenantTable1 = prepareForeignKey(TableWithoutTenantTableAnnotation.class.getName())
            def foreignKeyWithNullTable = prepareForeignKey(null)

            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def helper = Mock(ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper)
            def nameGenerator = Mock(NameGenerator)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getNameGenerator() >> nameGenerator
            posmultenUtilContext.getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper() >> helper

            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getForeignKeyIterator() >> { [foreignKeyWithTenantTable1, foreignKeyWithNullTable, foreignKeyWithTenantTable2, foreignKeyWithoutTenantTable1].iterator() }

            tested.init(configuration, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            1 * helper.enrichBuilder(builder, foreignKeyWithTenantTable1, nameGenerator)
            1 * helper.enrichBuilder(builder, foreignKeyWithTenantTable2, nameGenerator)
            0 * helper._

        and: "returned the same object of builder"
            result.is(builder)

        where:
            configuration << [new HashMap<>(), mapBuilder().put("hibernate.posmulten.foreignkey.constraint.ignore", "false").build(), mapBuilder().put("hibernate.posmulten.foreignkey.constraint.ignore", false).build()]
    }

    @Unroll
    def "should not enrich builder for foreign keys when property 'hibernate.posmulten.foreignkey.constraint.ignore' has value true, configuration #configuration"()
    {
        given:
            def foreignKeyWithTenantTable1 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithTenantTable2 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithoutTenantTable1 = prepareForeignKey(TableWithoutTenantTableAnnotation.class.getName())
            def foreignKeyWithNullTable = prepareForeignKey(null)

            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def helper = Mock(ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper)
            def nameGenerator = Mock(NameGenerator)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getNameGenerator() >> nameGenerator
            posmultenUtilContext.getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper() >> helper

            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getForeignKeyIterator() >> { [foreignKeyWithTenantTable1, foreignKeyWithNullTable, foreignKeyWithTenantTable2, foreignKeyWithoutTenantTable1].iterator() }

            tested.init(configuration, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * helper._

        and: "returned the same object of builder"
            result.is(builder)

        where:
            configuration << [mapBuilder().put("hibernate.posmulten.foreignkey.constraint.ignore", "true").build(), mapBuilder().put("hibernate.posmulten.foreignkey.constraint.ignore", true).build()]
    }

    def "should thrown an exception when enricher will not find class"()
    {
        given:
            def foreignKey = prepareForeignKey("no_such_class.in.project")
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def helper = Mock(ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper)
            def nameGenerator = Mock(NameGenerator)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getNameGenerator() >> nameGenerator
            posmultenUtilContext.getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper() >> helper

            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getForeignKeyIterator() >> { [foreignKey].iterator() }

            tested.init(null, serviceRegistryImplementor)

        when:
            tested.enrich(builder, metadata, table)

        then:
            def ex = thrown(RuntimeException.class)
            ex.cause instanceof ClassNotFoundException
    }

    private prepareForeignKey(String clazz)
    {
        def foreignKey = Mock(ForeignKey)
        foreignKey.getReferencedEntityName() >> clazz
        return foreignKey
    }

    @Override
    Map getMap() {
        return null
    }

    @Override
    ServiceRegistryImplementor getServiceRegistryImplementor() {
        return Mock(ServiceRegistryImplementor)
    }

    private static class TableWithoutTenantTableAnnotation {}

    @TenantTable
    private static class TableWithTableAnnotation {}
}
