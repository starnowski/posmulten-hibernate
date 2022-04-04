package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.core.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.setCurrentTenant;
import static org.assertj.core.api.Assertions.assertThat;

public class CRUDItTest extends AbstractBaseItTest {

    private static String uuid1 = "3b13cf9e-b39c-11ec-b909-0242ac120002";
    private static String uuid2 = "4b653630-b39c-11ec-b909-0242ac120002";
    private static String TENANT1 = "ten1";
    private static String TENANT2 = "ten2";
    private static String[] TENANT_IDS = {TENANT1, TENANT2};

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {TENANT1, new User().setUserId(UUID.fromString(uuid1)).setUsername("Simon")},
                {TENANT2, new User().setUserId(UUID.fromString(uuid2)).setUsername("johndoe")}
        };
    }

    @Test(dataProvider = "usersTenants", testName = "should create user for tenant", description = "should create user for tenant")
    public void shouldCreateUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.save(user);
            session.flush();
            transaction.commit();

            // THEN
            User current = session.find(User.class, user.getUserId());
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
            setCurrentTenant(tenant);
            try (Session session = openPrimarySession()) {

                // WHEN
                User current = session.find(User.class, user.getUserId());

                // THEN
                assertThat(current).isNull();
            }
        }
    }

    //TODO Not able to update for different tenant
    //TODO Update
    //TODO Not able to delete for different tenant
    //TODO Delete

    //TODO Change methods dependencies
    @Test(dependsOnMethods = "shouldNotAbleToReadRecordThatBelongsToDifferentTenants", dataProvider = "usersTenants", testName = "should delete user for tenant", description = "should delete user for tenant")
    public void shouldDeleteUsersPerTenants(String tenant, User user) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            user = session.find(User.class, user.getUserId());
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.delete(user);
            session.flush();
            transaction.commit();

            // THEN
            User current = session.find(User.class, user.getUserId());
            assertThat(current).isNull();
        }
    }
}
