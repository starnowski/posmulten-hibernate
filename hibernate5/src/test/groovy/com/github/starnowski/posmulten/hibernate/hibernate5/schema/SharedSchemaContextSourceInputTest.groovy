package com.github.starnowski.posmulten.hibernate.hibernate5.schema

import com.github.starnowski.posmulten.postgresql.core.common.SQLDefinition
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.stream.Collectors.toList

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

//    @Unroll
//    def "should prepare string array with sql definitions #expectedScripts" () {
//        given:
//            def sharedContext = Mock(ISharedSchemaContext)
//            def tested = new SharedSchemaContextSourceInput(sharedContext)
//            List<SQLDefinition> definitions = expectedScripts.stream().map({prepareSD(it)}).collect(toList())
//            sharedContext.getSqlDefinitions() >> definitions
//            tested.prepare()
//            ImportSqlCommandExtractor importSqlCommandExtractor = Mock(ImportSqlCommandExtractor)
//            1 * importSqlCommandExtractor.extractCommands(_) >> { Reader r ->
//                if (r instanceof  StringReader)
//                {
//                    char[] buffer = new char[1024]
//                    int length = r.read(buffer, 0, 1024)
//                    String[] array = new String[1]
//                    array[0] = new String(buffer).substring(0, length)
//                    return array
//                }
//                return null
//            }
//
//        when:
//            def results = tested.read(importSqlCommandExtractor)
//
//        then:
//            results == expectedScripts
//
//        where:
//            expectedScripts << [["SELECT * FROM schema_info;", "SELECT 1"], ["SELECT * FROM dual;", "SELECT * from users", "select 1 from posts"]]
//    }

    /**
     * https://github.com/starnowski/posmulten/issues/233
     * https://github.com/starnowski/posmulten/issues/234
     */
    @Unroll
    def "should prepare string array with sql definitions #expectedScripts" () {
        given:
            def sharedContext = Mock(ISharedSchemaContext)
            def tested = new SharedSchemaContextSourceInput(sharedContext)
            List<SQLDefinition> definitions = expectedScripts.stream().map({prepareSD(it)}).collect(toList())
            sharedContext.getSqlDefinitions() >> definitions
            tested.prepare()
            ImportSqlCommandExtractor importSqlCommandExtractor = Mock(ImportSqlCommandExtractor)

        when:
            def results = tested.read(importSqlCommandExtractor)

        then:
            results == expectedScripts
            0 * importSqlCommandExtractor._

        where:
            expectedScripts << [["SELECT * FROM schema_info;", "SELECT 1"], ["SELECT * FROM dual;", "SELECT * from users", "select 1 from posts"]]
    }

    private SQLDefinition prepareSD(String creationScript)
    {
        SQLDefinition sqlDefinition = Mock(SQLDefinition)
        sqlDefinition.getCreateScript() >> creationScript
        return sqlDefinition;
    }
}
