package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import org.hibernate.mapping.Constraint
import org.hibernate.mapping.Table
import org.mockito.MockedStatic
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Collections.emptyList

class NameGeneratorTest extends Specification {

    static MockedStatic<Constraint> generatorMock

    def setupSpec()
    {
        generatorMock = org.mockito.Mockito.mockStatic(Constraint.class)
    }

    @Unroll
    def "should return name with prefix #prefix generated by static method (#generatedString) and shorten for maximum length : #maxLength, #expectedString" () {
        given:
            def tested = new NameGenerator(maxLength)
            def table = Mock(Table)
            generatorMock.when({  -> Constraint.generateName(prefix, table, emptyList()) }).thenReturn(generatedString)

        when:
            def result = tested.generate(prefix, table)

        then:
            result == expectedString

        where:
            prefix              |   maxLength   |   generatedString                                         ||  expectedString
            "xxx_"              |   25          |   "some_long_string_longer_then_25_characters"            ||  "some_long_string_longer_t"
            "rls_policy"        |   15          |   "generated_string_by_static_method"                     ||  "generated_strin"
            "posmulte_pref"     |   70          |   "normal_string_that_does_not_reach_max_length_limit"    ||  "normal_string_that_does_not_reach_max_length_limit"
    }
}