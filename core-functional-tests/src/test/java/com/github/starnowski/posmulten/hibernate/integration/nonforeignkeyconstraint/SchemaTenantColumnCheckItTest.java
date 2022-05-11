package com.github.starnowski.posmulten.hibernate.integration.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.*;
import com.github.starnowski.posmulten.hibernate.test.utils.ReflectionUtils;
import com.github.starnowski.posmulten.hibernate.test.utils.TestUtils;
import org.hibernate.jdbc.ReturningWork;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class SchemaTenantColumnCheckItTest extends AbstractBaseNonForeignKeyConstraintItTest {

    @DataProvider(name = "defaultTenantColumn")
    protected static Object[][] defaultTenantColumn() {
        return new Object[][]{
                {Post.class, "posts_nonforeignkeyconstraint"}
        };
    }

    @DataProvider(name = "customTenantColumn")
    protected static Object[][] customTenantColumn() {
        return new Object[][]{
                {Comment.class, "comments_nonforeignkeyconstraint", "comment_tenant_id"},
                {Category.class, "categories_nonforeignkeyconstraint", "categorytenantid"},
                {User.class, "user_info_nonforeignkeyconstraint"},
                {UserRole.class, "user_role_nonforeignkeyconstraint"}
        };
    }

    @Test(dataProvider = "defaultTenantColumn", testName = "should create a default tenant column when the mapped class has TenantTable annotation with default values and without a field with the same name as a default column value", description = "should create a default tenant column when the mapped class has TenantTable annotation with default values and without a field with the same name as a default column value")
    public void shouldCreateDefaultTenantColumn(Class clazz, String tableName) {
        // GIVEN
        assertThat(ReflectionUtils.classContainsProperty(clazz, "tenant_id")).isFalse();
        assertThat(ReflectionUtils.classContainsProperty(clazz, "tenantId")).isFalse();

        // WHEN
        String result = schemaCreatorSession.doReturningWork(new ReturningWork<String>() {
            public String execute(Connection connection) throws SQLException {
                return TestUtils.selectAndReturnFirstRecordAsString(connection.createStatement(), TestUtils.statementThatReturnsColumnNameAndType(tableName, "tenant_id", "public", "posmulten_hibernate"));
            }
        });

        // THEN
        assertThat(result).isEqualTo("tenant_id character varying(255)");
    }

    @Test(dataProvider = "customTenantColumn", testName = "should create a custom tenant column when the mapped class has TenantTable annotation with specified tenant column name and without a field with the same name as a default column value", description = "should create a custom tenant column when the mapped class has TenantTable annotation with specified tenant column name and without a field with the same name as a default column value")
    public void shouldCreateCustomTenantColumn(Class clazz, String tableName, String columnName) {
        // GIVEN
        assertThat(ReflectionUtils.classContainsProperty(clazz, columnName)).isFalse();

        // WHEN
        String result = schemaCreatorSession.doReturningWork(new ReturningWork<String>() {
            public String execute(Connection connection) throws SQLException {
                return TestUtils.selectAndReturnFirstRecordAsString(connection.createStatement(), TestUtils.statementThatReturnsColumnNameAndType(tableName, columnName, "public", "posmulten_hibernate"));
            }
        });

        // THEN
        assertThat(result).isEqualTo(columnName + " character varying(255)");
    }

    @Test(testName = "should create a default tenant column in join table", description = "should create a default tenant column in join table")
    public void shouldCreateDefaultTenantColumnInJoinTable() {
        // GIVEN

        // WHEN
        String result = schemaCreatorSession.doReturningWork(new ReturningWork<String>() {
            public String execute(Connection connection) throws SQLException {
                return TestUtils.selectAndReturnFirstRecordAsString(connection.createStatement(), TestUtils.statementThatReturnsColumnNameAndType("posts_categories", "tenant_id", "public", "posmulten_hibernate"));
            }
        });

        // THEN
        assertThat(result).isEqualTo("tenant_id character varying(255)");
    }
}
