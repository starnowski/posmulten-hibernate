package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.test.utils.TestUtils;
import org.hibernate.jdbc.ReturningWork;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class SchemaTenantColumnCheckItTest extends AbstractBaseItTest {

    @Test
    public void shouldCreateDefaultTenantColumn()
    {
        // GIVEN
        //TODO Check that class does not have any property named tenantId

        // WHEN
        String result = schemaCreatorSession.doReturningWork(new ReturningWork<String>() {
            public String execute(Connection connection) throws SQLException {
                return TestUtils.selectAndReturnFirstRecordAsString(connection.createStatement(), TestUtils.statementThatReturnsColumnNameAndType("posts", "tenant_id", "public"));
            }
        });

        // THEN
        assertThat(result).isEqualTo("tenant_id VARCHAR(255)");
    }
}
