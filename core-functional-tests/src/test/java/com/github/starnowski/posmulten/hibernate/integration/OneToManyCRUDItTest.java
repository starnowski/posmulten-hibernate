package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.core.model.Post;
import com.github.starnowski.posmulten.hibernate.core.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.setCurrentTenant;
import static org.assertj.core.api.Assertions.assertThat;

public class OneToManyCRUDItTest extends AbstractBaseItTest {

    private static final String TENANT1 = "ten_1";
    private static final String TENANT2 = "ten_2";
    private static final String[] TENANT_IDS = {TENANT1, TENANT2};

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {TENANT1, new User().setUsername("Mike")},
                {TENANT1, new User().setUsername("Bill")},
                {TENANT2, new User().setUsername("Jake")}
        };
    }

    @DataProvider(name = "posts")
    protected static Object[][] posts() {
        return new Object[][]{
                {TENANT1, new Post().setText("post1"), "Mike"},
                {TENANT1, new Post().setText("post2"), "Mike"},
                {TENANT1, new Post().setText("post3"), "Mike"},
                {TENANT2, new Post().setText("post11"), "Jake"},
                {TENANT2, new Post().setText("post12"), "Jake"},
                {TENANT2, new Post().setText("post13"), "Jake"},
                {TENANT1, new Post().setText("post21"), "Bill"},
                {TENANT1, new Post().setText("post22"), "Bill"},
                {TENANT1, new Post().setText("post23"), "Bill"}
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

    @Test(dataProvider = "posts", dependsOnMethods = "shouldCreateUsersPerTenants", testName = "should create post with user for tenant", description = "should create post with user for tenant")
    public void shouldCreatePostsAndAttachToUserPerTenants(String tenant, Post post, String username) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            User user = findUserByUsername(session, username);
            post.setAuthor(user);
            session.persist(post);
            session.flush();
            transaction.commit();

            // THEN
            Post current = findPostByText(session, post.getText());
            assertThat(current).isNotNull();
            assertThat(current.getAuthor()).isNotNull();
            assertThat(current.getAuthor().getUserId()).isEqualTo(user.getUserId());
        }
    }

    @Test(dependsOnMethods = {"shouldCreatePostsAndAttachToUserPerTenants"}, dataProvider = "posts", testName = "should delete posts for tenant", description = "should delete posts for tenant")
    public void shouldDeletePostsPerTenants(String tenant, Post post, String username) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            post = findPostByText(session, post.getText());
            Transaction transaction = session.beginTransaction();

            // WHEN
            session.delete(post);
            session.flush();
            transaction.commit();

            // THEN
            Post current = findPostByText(session, post.getText());
            assertThat(current).isNull();
        }
    }

    @Test(dependsOnMethods = {"shouldDeletePostsPerTenants"}, dataProvider = "usersTenants", testName = "should delete user for tenant", description = "should delete user for tenant")
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

    private User findUserByUsername(Session session, String username) {
        Query<User> query = session.createQuery("FROM User as user WHERE user.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    private Post findPostByText(Session session, String postText) {
        Query<Post> query = session.createQuery("FROM Post as post WHERE post.text = :text", Post.class);
        query.setParameter("text", postText);
        return query.uniqueResult();
    }
}
