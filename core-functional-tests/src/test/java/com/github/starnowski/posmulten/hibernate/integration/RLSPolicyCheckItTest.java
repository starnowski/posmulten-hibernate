package com.github.starnowski.posmulten.hibernate.integration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.isFunctionExists;
import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.selectAndReturnFirstRecordAsLong;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;


public class RLSPolicyCheckItTest extends AbstractBaseItTest {

    @DataProvider(name = "tenantTables")
    protected static Object[][] tenantTables() {
        return new Object[][]{
                {"posts"},
                {"user_info"},
                {"comments"},
                {"categories"},
                {"user_role"},
                {"posts_categories"},
                {"categories_category_types"} // One of relation table is dictionary table but still RLS policy should be created
        };
    }

    @DataProvider(name = "noneTenantTables")
    protected static Object[][] noneTenantTables() {
        return new Object[][]{
                {"category_types_category_type_attributes"} // Both tables are dictionary tables
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

    @Test(dataProvider = "noneTenantTables", testName = "should not create RLS policy for table", description = "should not create RLS policy for table")
    public void shouldNotCreateRLSPolicy(String tableName) {
        // GIVEN
        String schemaName = "public";
        Boolean tableExists = schemaCreatorSession.doReturningWork(connection -> isFunctionExists(connection.createStatement(), tableName, schemaName));
        assertThat(tableExists).isTrue();

        // WHEN
        Long result = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM pg_catalog.pg_policy pg, pg_class pc, pg_catalog.pg_namespace pn WHERE pg.polrelid = pc.oid AND pc.relnamespace = pn.oid AND pc.relname = '%1$s' AND pn.nspname = '%2$s';", tableName, schemaName)));

        // THEN
        assertThat(result).isZero();
    }

}
