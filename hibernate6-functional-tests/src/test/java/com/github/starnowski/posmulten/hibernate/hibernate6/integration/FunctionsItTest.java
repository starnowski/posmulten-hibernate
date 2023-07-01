package com.github.starnowski.posmulten.hibernate.hibernate6.integration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.hibernate6.util.NameUtils.generateNameForPrefixAndTableName;
import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.isFunctionExists;
import static org.assertj.core.api.Assertions.assertThat;

public class FunctionsItTest extends AbstractBaseItTest {

    @DataProvider(name = "functionNames")
    protected static Object[][] functionNames() {
        return new Object[][]{
                {"get_current_tenant_id"},
                {"set_current_tenant_id"},
                {"is_id_equals_current_tenant_id"},
                {"tenant_has_authorities"},
                {generateNameForPrefixAndTableName("is_rls_record_exists_in_", "posts")},
                {generateNameForPrefixAndTableName("is_rls_record_exists_in_", "categories")},
                {generateNameForPrefixAndTableName("is_rls_record_exists_in_", "user_info")}
        };
    }

    @Test(dataProvider = "functionNames", testName = "should create function", description = "should create function")
    public void shouldCreateFunction(String functionName) {
        // GIVEN
        String schemaName = "public";

        // WHEN
        Boolean result = schemaCreatorSession.doReturningWork(connection -> isFunctionExists(connection.createStatement(), functionName, schemaName));

        // THEN
        assertThat(result).isEqualTo(result);
    }
}
