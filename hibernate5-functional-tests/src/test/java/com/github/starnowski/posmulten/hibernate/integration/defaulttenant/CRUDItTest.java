package com.github.starnowski.posmulten.hibernate.integration.defaulttenant;

import com.github.starnowski.posmulten.hibernate.hibernate5.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.postgresql.util.PSQLException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;

import static com.github.starnowski.posmulten.hibernate.common.context.CurrentTenantContext.setCurrentTenant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CRUDItTest extends AbstractBaseItTest {

    private static final String TENANT1 = "ten1";
    private static final String TENANT2 = "ten2";
    private static final String[] TENANT_IDS = {TENANT1, TENANT2};

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {TENANT1, new User().setUsername("Simon")},
                {TENANT2, new User().setUsername("johndoe")}
        };
    }

    @Test(dataProvider = "usersTenants", testName = "should create user for tenant", description = "should create user for tenant")
    public void shouldCreateUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.persist(user);
            session.flush();
            transaction.commit();

            // THEN
            User current = findUserByUsername(session, user.getUsername());
            assertThat(current).isNotNull();
            assertThat(current.getUserId()).isEqualTo(user.getUserId());
        }
    }

    @Test(dependsOnMethods = "shouldCreateUsersPerTenants", dataProvider = "usersTenants", testName = "should not able to read records that belongs to different tenant", description = "should not able to read records that belongs to different tenant")
    public void shouldNotAbleToReadRecordThatBelongsToDifferentTenants(String tenant, User user) {
        for (String tt : TENANT_IDS) {
            if (tt.equals(tenant)) {
                continue;
            }
            // GIVEN
            setCurrentTenant(tt);
            try (Session session = openPrimarySession()) {

                // WHEN
                User current = findUserByUsername(session, user.getUsername());

                // THEN
                assertThat(current).isNull();
            }
        }
    }

    @Test(dependsOnMethods = "shouldCreateUsersPerTenants", dataProvider = "usersTenants", testName = "should read created user for tenant", description = "should read created user for tenant")
    public void shouldReadCreateUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            // WHEN
            User current = findUserByUsername(session, user.getUsername());

            // THEN
            assertThat(current).isNotNull();
            assertThat(current.getUsername()).isEqualTo(user.getUsername());
        }
    }

    @Test(dependsOnMethods = {"shouldReadCreateUsersPerTenants"}, dataProvider = "usersTenants", testName = "should try update user for different tenant", description = "should try update user for different tenant")
    public void shouldTryToUpdateUsersFromDifferentTenants(String tenant, User user) {
        for (String tt : TENANT_IDS) {
            if (tt.equals(tenant)) {
                continue;
            }
            // GIVEN
            setCurrentTenant(tt);
            try (Session session = openPrimarySession()) {
                Transaction transaction = session.beginTransaction();

                // WHEN
                int numberOfDeleteRecords = session.createNativeQuery(String.format("UPDATE user_info SET password = 'YYY' WHERE username = '%s'", user.getUsername())).executeUpdate();
                session.flush();
                transaction.commit();

                // THEN
                assertThat(numberOfDeleteRecords).isZero();
            }
        }
    }

    @Test(dependsOnMethods = "shouldTryToUpdateUsersFromDifferentTenants", dataProvider = "usersTenants", testName = "should update created user for tenant", description = "should update created user for tenant")
    public void shouldUpdateCreateUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();
            user = findUserByUsername(session, user.getUsername());
            assertThat(user.getPassword()).isNull();
            user.setPassword("XXX");

            // WHEN
            user = (User) session.merge(user);
            session.flush();
            transaction.commit();

            // THEN
            user = findUserByUsername(session, user.getUsername());
            assertThat(user).isNotNull();
            assertThat(user.getPassword()).isEqualTo("XXX");
        }
    }

    @Test(dependsOnMethods = "shouldUpdateCreateUsersPerTenants", dataProvider = "usersTenants", testName = "should update created user for tenant with native query", description = "should update created user for tenant with native query")
    public void shouldUpdateCreateUsersPerTenantsWithNativeQuery(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            int numberOfUpdatedRecords = session.createNativeQuery(String.format("UPDATE user_info SET password = 'ZZZ' WHERE username = '%s'", user.getUsername())).executeUpdate();
            session.flush();
            transaction.commit();

            // THEN
            assertThat(numberOfUpdatedRecords).isEqualTo(1);
            user = findUserByUsername(session, user.getUsername());
            assertThat(user).isNotNull();
            assertThat(user.getPassword()).isEqualTo("ZZZ");
        }
    }

    @Test(dependsOnMethods = {"shouldUpdateCreateUsersPerTenantsWithNativeQuery"}, dataProvider = "usersTenants", testName = "should try delete user for different tenant", description = "should try delete user for different tenant")
    public void shouldTryToDeleteUsersFromDifferentTenants(String tenant, User user) {
        for (String tt : TENANT_IDS) {
            if (tt.equals(tenant)) {
                continue;
            }
            // GIVEN
            setCurrentTenant(tt);
            try (Session session = openPrimarySession()) {
                Transaction transaction = session.beginTransaction();

                // WHEN
                int numberOfDeleteRecords = session.createNativeQuery(String.format("DELETE FROM user_info WHERE username = '%s'", user.getUsername())).executeUpdate();
                session.flush();
                transaction.commit();

                // THEN
                assertThat(numberOfDeleteRecords).isZero();
            }
        }
    }

    @Test(dependsOnMethods = {"shouldTryToDeleteUsersFromDifferentTenants"}, dataProvider = "usersTenants", testName = "should delete user for tenant", description = "should delete user for tenant")
    public void shouldDeleteUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            user = findUserByUsername(session, user.getUsername());
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.delete(user);
            session.flush();
            transaction.commit();

            // THEN
            User current = findUserByUsername(session, user.getUsername());
            assertThat(current).isNull();
        }
    }

    @Test(dataProvider = "usersTenants", testName = "should try to create users for invalid tenant", description = "should try to create users for invalid tenant")
    public void shouldTryToCreateUsersForInvalidTenant(String tenant, User user) {
        // GIVEN
        setCurrentTenant(INVALID_TENANT);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.persist(user);

            // THEN
            assertThatThrownBy(() ->
                    session.flush()
            ).isInstanceOf(PersistenceException.class).getRootCause().isInstanceOf(PSQLException.class).hasMessage("ERROR: new row for relation \"user_info\" violates check constraint \"tenant_identifier_valid\"");
        }
    }

    private User findUserByUsername(Session session, String username) {
        Query<User> query = session.createQuery("FROM User as user WHERE user.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
}
