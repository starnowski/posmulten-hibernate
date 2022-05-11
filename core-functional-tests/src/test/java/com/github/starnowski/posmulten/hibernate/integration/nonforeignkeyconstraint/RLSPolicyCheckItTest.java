package com.github.starnowski.posmulten.hibernate.integration.nonforeignkeyconstraint;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.selectAndReturnFirstRecordAsLong;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;


public class RLSPolicyCheckItTest extends AbstractBaseNonForeignKeyConstraintItTest {

    @DataProvider(name = "tenantTables")
    protected static Object[][] tenantTables() {
        return new Object[][]{
                {"posts_nonforeignkeyconstraint"},
                {"user_info_nonforeignkeyconstraint"},
                {"comments_nonforeignkeyconstraint"},
                {"categories_nonforeignkeyconstraint"},
                {"user_role_nonforeignkeyconstraint"},
                {"posts_categories_nonforeignkeyconstraint"}
        };
    }

    @Test(dataProvider = "tenantTables", testName = "should create RLS policy for table", description = "should create RLS policy for table")
    public void shouldCreateRLSPolicy(String tableName) {
        // GIVEN
        String schemaName = "public";

        // WHEN
        Long result = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM pg_catalog.pg_policy pg, pg_class pc, pg_catalog.pg_namespace pn WHERE pg.polrelid = pc.oid AND pc.relnamespace = pn.oid AND pc.relname = '%1$s' AND pn.nspname = '%2$s';", tableName, schemaName)));

        // THEN
        assertThat(result).isEqualTo(1L);
    }

}
