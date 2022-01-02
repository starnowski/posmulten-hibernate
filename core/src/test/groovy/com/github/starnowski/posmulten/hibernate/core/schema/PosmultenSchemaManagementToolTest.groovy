package com.github.starnowski.posmulten.hibernate.core.schema

import org.hibernate.tool.schema.internal.SchemaCreatorImpl
import spock.lang.Specification

class PosmultenSchemaManagementToolTest extends Specification {

    def "should return schema creator wrapper" ()
    {
        given:
            //TODO init schema tool with serviceRegistry
            def tested = new PosmultenSchemaManagementTool()

        when:
            def result = tested.getSchemaCreator(null)

        then:
            result.is(SchemaCreatorWrapper)
            SchemaCreatorWrapper schemaCreatorWrapper = (SchemaCreatorWrapper)result
            schemaCreatorWrapper.getWrappedSchemaCreator().is(SchemaCreatorImpl)
    }
}
