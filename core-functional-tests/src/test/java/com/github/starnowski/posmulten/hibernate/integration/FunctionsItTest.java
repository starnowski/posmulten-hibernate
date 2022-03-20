package com.github.starnowski.posmulten.hibernate.integration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.isFunctionExists;
import static org.assertj.core.api.Assertions.assertThat;

public class FunctionsItTest extends AbstractBaseItTest {

    @DataProvider(name = "defaultFunctions")
    protected static Object[][] defaultFunctions() {
        return new Object[][]{
                {"get_current_tenant_id"},
                {"set_current_tenant_id"},
                {"is_id_equals_current_tenant_id"},
                {"tenant_has_authorities"}
        };
    }

    @Test(dataProvider = "defaultFunctions", testName = "should create default function", description = "should create default function")
    public void shouldCreateDefaultFunctions(String functionName) {
        // GIVEN
        String schemaName = "public";

        // WHEN
        Boolean result = schemaCreatorSession.doReturningWork(connection -> isFunctionExists(connection.createStatement(), functionName, schemaName));

        // THEN
        assertThat(result).isEqualTo(result);
    }
}
