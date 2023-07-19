package com.github.starnowski.posmulten.hibernate.hibernate6.integration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.hibernate6.util.NameUtils.generateNameForPrefixAndTableNameAndColumnsNames;
import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.isConstraintExists;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintItTest extends AbstractBaseItTest {

    @DataProvider(name = "constraintNames")
    protected static Object[][] constraintNames() {
        return new Object[][]{
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "comments", "post_id"), "comments"},
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "posts_categories", "posts_id"), "posts_categories"},
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "user_role", "user_id"), "user_role"},
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "posts", "userId"), "posts"},
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "posts_categories", "categories_id"), "posts_categories"},
                {generateNameForPrefixAndTableNameAndColumnsNames("rls_fk_con_", "comments", "userId"), "comments"}
        };
    }

    @Test(dataProvider = "constraintNames", testName = "should create constraint", description = "should create constraint")
    public void shouldCreateConstraint(String constraintName, String tableName) {
        // GIVEN
        String schemaName = "public";

        // WHEN
        Boolean result = schemaCreatorSession.doReturningWork(connection -> isConstraintExists(connection.createStatement(), schemaName, tableName, constraintName));

        // THEN
        assertThat(result).isEqualTo(result);
    }
}
