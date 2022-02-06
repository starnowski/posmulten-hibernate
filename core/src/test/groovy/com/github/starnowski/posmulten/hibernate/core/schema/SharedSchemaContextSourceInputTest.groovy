package com.github.starnowski.posmulten.hibernate.core.schema

import com.github.starnowski.posmulten.postgresql.core.common.SQLDefinition
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import spock.lang.Specification

class SharedSchemaContextSourceInputTest extends Specification {

    def "should prepare sql definitions" () {
        given:
            def sharedContext = Mock(ISharedSchemaContext)
            def tested = new SharedSchemaContextSourceInput(sharedContext)
            tested.definitions == null
            List<SQLDefinition> definitions = Arrays.asList(Mock(SQLDefinition))

        when:
            tested.prepare()

        then:
            1 * sharedContext.getSqlDefinitions() >> definitions
            tested.definitions == definitions
    }

    def "should release sql definitions" () {
        given:
            def sharedContext = Mock(ISharedSchemaContext)
            def tested = new SharedSchemaContextSourceInput(sharedContext)
            tested.definitions == null
            List<SQLDefinition> definitions = Arrays.asList(Mock(SQLDefinition))
            sharedContext.getSqlDefinitions() >> definitions
            tested.prepare()
            tested.definitions == definitions

        when:
            tested.release()

        then:
            tested.definitions == null
    }
}
