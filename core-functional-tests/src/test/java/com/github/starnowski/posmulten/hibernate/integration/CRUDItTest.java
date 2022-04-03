package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.core.model.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CRUDItTest extends AbstractBaseItTest {

    private static String uuid1 = "3b13cf9e-b39c-11ec-b909-0242ac120002";
    private static String uuid2 = "4b653630-b39c-11ec-b909-0242ac120002";

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {"ten1", new User().setUserId(UUID.fromString(uuid1)).setUsername("Simon")},
                {"ten2", new User().setUserId(UUID.fromString(uuid2)).setUsername("johndoe")}
        };
    }

    @Test(dataProvider = "usersTenants", testName = "should create user for tenant", description = "should create user for tenant")
    public void shouldCreateDefaultTenantColumn(User user, String tenant) {
        // GIVEN
        //TODO Set tenant

        // WHEN
        primarySession.persist(user);

        // THEN
        User current = primarySession.find(User.class, user.getUserId());
        assertThat(current).isNotNull();
        assertThat(current.getUserId()).isEqualTo(user.getUserId());
    }
}
