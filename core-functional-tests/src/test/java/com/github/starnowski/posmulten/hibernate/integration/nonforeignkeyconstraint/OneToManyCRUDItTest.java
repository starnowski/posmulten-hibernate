package com.github.starnowski.posmulten.hibernate.integration.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.Post;
import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.StringPrimaryKey;
import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.User;
import com.github.starnowski.posmulten.hibernate.integration.AbstractBaseItTest;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.setCurrentTenant;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class OneToManyCRUDItTest extends AbstractBaseNonForeignKeyConstraintItTest {

    private static final String TENANT1 = "ten_1";
    private static final String TENANT2 = "ten_2";
    private static final String[] TENANT_IDS = {TENANT1, TENANT2};

    @DataProvider(name = "usersTenants")
    protected static Object[][] userTenants() {
        return new Object[][]{
                {TENANT1, new User().setPrimaryKey(new StringPrimaryKey().withStringKey("key1").withTenant(TENANT1)).setUsername("Mike")},
                {TENANT1, new User().setPrimaryKey(new StringPrimaryKey().withStringKey("key2").withTenant(TENANT1)).setUsername("Bill")},
                {TENANT2, new User().setPrimaryKey(new StringPrimaryKey().withStringKey("key3").withTenant(TENANT2)).setUsername("Jake")}
        };
    }

    @DataProvider(name = "posts")
    protected static Object[][] posts() {
        return new Object[][]{
                {TENANT1, new Post().setTenant(TENANT1).setText("post1"), "Mike"},
                {TENANT1, new Post().setTenant(TENANT1).setText("post2"), "Mike"},
                {TENANT1, new Post().setTenant(TENANT1).setText("post3"), "Mike"},
                {TENANT2, new Post().setTenant(TENANT2).setText("post11"), "Jake"},
                {TENANT2, new Post().setTenant(TENANT2).setText("post12"), "Jake"},
                {TENANT2, new Post().setTenant(TENANT2).setText("post13"), "Jake"},
                {TENANT1, new Post().setTenant(TENANT1).setText("post21"), "Bill"},
                {TENANT1, new Post().setTenant(TENANT1).setText("post22"), "Bill"},
                {TENANT1, new Post().setTenant(TENANT1).setText("post23"), "Bill"}
        };
    }

    @DataProvider(name = "expectedPostsForUsers")
    protected static Object[][] expectedPostsForUsers() {
        return new Object[][]{
                {TENANT1, "Mike", new String[]{"post1", "post2", "post3"}},
                {TENANT1, "Bill", new String[]{"post21", "post22", "post23"}},
                {TENANT2, "Jake", new String[]{"post11", "post12", "post13"}}
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
            assertThat(current.getPrimaryKey()).isNotNull();
            assertThat(current.getPrimaryKey().getStringKey()).isNotNull();
            assertThat(current.getPrimaryKey().getTenant()).isNotNull();
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
//            post.setAuthorKey(user.getPrimaryKey().getStringKey());
            session.persist(post);
            session.flush();
            transaction.commit();

            // THEN
            Post current = findPostByText(session, post.getText());
            assertThat(current).isNotNull();
            assertThat(current.getAuthor()).isNotNull();
            assertThat(current.getAuthor().getPrimaryKey()).isEqualTo(user.getPrimaryKey());
        }
    }

    @Test(dependsOnMethods = "shouldCreatePostsAndAttachToUserPerTenants", dataProvider = "expectedPostsForUsers", testName = "should read created user for tenant and validate its posts", description = "should read created user for tenant and validate its posts")
    public void shouldReadPostsAndValidatedAttachmentToUser(String tenant, String username, String... expectedPostsTexts) {
        // GIVEN
        setCurrentTenant(tenant);
        try (Session session = openPrimarySession()) {
            // WHEN
            User current = findUserByUsername(session, username);
            Hibernate.initialize(current.getPosts());

            // THEN
            assertThat(current.getPosts()).isNotEmpty();
            assertThat((current.getPosts().stream().map(Post::getText).collect(toSet()))).isEqualTo(new HashSet<>(asList(expectedPostsTexts)));
        }
    }

    @Test(dependsOnMethods = {"shouldReadPostsAndValidatedAttachmentToUser"}, dataProvider = "posts", testName = "should delete posts for tenant", description = "should delete posts for tenant")
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
