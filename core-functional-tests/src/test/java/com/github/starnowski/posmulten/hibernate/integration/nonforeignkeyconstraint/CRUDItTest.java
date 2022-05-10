package com.github.starnowski.posmulten.hibernate.integration.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.model.User;
import com.github.starnowski.posmulten.hibernate.integration.AbstractBaseItTest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.setCurrentTenant;
import static org.assertj.core.api.Assertions.assertThat;

public class CRUDItTest extends AbstractBaseItTest {

    private static String TENANT1 = "ten1";
    private static String TENANT2 = "ten2";
    private static String[] TENANT_IDS = {TENANT1, TENANT2};

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

    //TODO Not able to update for different tenant

    @Test(dependsOnMethods = "shouldReadCreateUsersPerTenants", dataProvider = "usersTenants", testName = "should update created user for tenant", description = "should update created user for tenant")
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
            assertThat(user).isNotNull();
            assertThat(user.getPassword()).isEqualTo("XXX");
        }
    }
    //TODO Not able to delete for different tenant
    //TODO Delete

    //TODO Change methods dependencies
    @Test(dependsOnMethods = {"shouldNotAbleToReadRecordThatBelongsToDifferentTenants", "shouldUpdateCreateUsersPerTenants"}, dataProvider = "usersTenants", testName = "should delete user for tenant", description = "should delete user for tenant")
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

    private User findUserByUsername(Session session, String username)
    {
        Query<User> query = session.createQuery("FROM User as user WHERE user.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
}
