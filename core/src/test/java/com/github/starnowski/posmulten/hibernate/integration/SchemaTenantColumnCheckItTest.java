package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.core.model.Post;
import com.github.starnowski.posmulten.hibernate.core.model.User;
import com.github.starnowski.posmulten.hibernate.core.model.UserRole;
import com.github.starnowski.posmulten.hibernate.test.utils.ReflectionUtils;
import com.github.starnowski.posmulten.hibernate.test.utils.TestUtils;
import org.hibernate.jdbc.ReturningWork;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class SchemaTenantColumnCheckItTest extends AbstractBaseItTest {

    @DataProvider(name = "defaultTenantColumn")
    protected static Object[][] postData()
    {
        return new Object[][]{
                {Post.class, "posts"},
                {User.class, "user_info"},
                {UserRole.class, "user_role"}
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
}
