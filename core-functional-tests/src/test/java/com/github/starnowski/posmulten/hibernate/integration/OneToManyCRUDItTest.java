package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.core.model.Post;
import com.github.starnowski.posmulten.hibernate.core.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;

public class OneToManyCRUDItTest extends AbstractBaseItTest {

    private static final String TENANT1 = "ten_1";
    private static final String TENANT2 = "ten_2";
    private static final String[] TENANT_IDS = {TENANT1, TENANT2};

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {TENANT1, new User().setUsername("Simon")},
                {TENANT2, new User().setUsername("johndoe")}
        };
    }

    @DataProvider(name = "posts")
    protected static Object[][] posts() {
        return new Object[][]{
                {TENANT1, new Post().setText("post1")},
                {TENANT1, new Post().setText("post2")},
                {TENANT1, new Post().setText("post3")},
                {TENANT2, new Post().setText("post11")},
                {TENANT2, new Post().setText("post12")},
                {TENANT2, new Post().setText("post13")}
        };
    }

    private User findUserByUsername(Session session, String username) {
        Query<User> query = session.createQuery("FROM User as user WHERE user.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
}
