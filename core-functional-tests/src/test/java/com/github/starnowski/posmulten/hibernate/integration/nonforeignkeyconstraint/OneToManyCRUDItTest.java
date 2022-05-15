package com.github.starnowski.posmulten.hibernate.integration.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.Post;
import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.StringPrimaryKey;
import com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;

import static com.github.starnowski.posmulten.hibernate.core.context.CurrentTenantContext.setCurrentTenant;
import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.selectAndReturnFirstRecordAsLong;
import static com.github.starnowski.posmulten.hibernate.test.utils.TestUtils.selectAndReturnFirstRecordAsString;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DataProvider(name = "postsAndUsersFromDifferentTenant")
    protected static Object[][] postsAndUsersFromDifferentTenant() {
        return new Object[][]{
                {TENANT1, "post1", TENANT2, "Jake"},
                {TENANT1, "post2", TENANT2, "Jake"},
                {TENANT1, "post3", TENANT2, "Jake"},
                {TENANT2, "post11", TENANT1, "Bill"},
                {TENANT2, "post12", TENANT1, "Bill"},
                {TENANT2, "post13", TENANT1, "Mike"},
                {TENANT1, "post21", TENANT2, "Jake"},
                {TENANT1, "post22", TENANT2, "Jake"},
                {TENANT1, "post23", TENANT2, "Jake"}
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

    @Test(dependsOnMethods = "shouldReadPostsAndValidatedAttachmentToUser", dataProvider = "postsAndUsersFromDifferentTenant", testName = "should try to update post with user id from different tenant with native query", description = "should try to update post with user id from different tenant with native query")
    public void shouldUpdateCreateUsersPerTenantsWithNativeQuery(String postTenant, String postText, String userTenant, String userName) {
        // GIVEN
        Long numberOfPostsForPostTenant = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM posts_nonforeignkeyconstraint WHERE text = '%1$s' AND tenant_id = '%2$s';", postText, postTenant)));
        Long numberOfPostsForNonPostTenant = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM posts_nonforeignkeyconstraint WHERE text = '%1$s' AND tenant_id = '%2$s';", postText, userTenant)));
        Long numberOfUsersForUserTenant = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM user_info_nonforeignkeyconstraint WHERE username = '%1$s' AND tenant = '%2$s';", userName, userTenant)));
        Long numberOfUsersForNonUserTenant = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsLong(connection.createStatement(), format("SELECT COUNT(1) FROM user_info_nonforeignkeyconstraint WHERE username = '%1$s' AND tenant = '%2$s';", userName, postTenant)));
        String userId = schemaCreatorSession.doReturningWork(connection -> selectAndReturnFirstRecordAsString(connection.createStatement(), format("SELECT user_id FROM user_info_nonforeignkeyconstraint WHERE username = '%1$s' AND tenant = '%2$s';", userName, userTenant)));

        assertThat(numberOfPostsForPostTenant).isOne();
        assertThat(numberOfPostsForNonPostTenant).isZero();
        assertThat(numberOfUsersForUserTenant).isOne();
        assertThat(numberOfUsersForNonUserTenant).isZero();

        setCurrentTenant(postTenant);
        try (Session session = openPrimarySession()) {
            session.beginTransaction();

            assertThatThrownBy(() ->
                    session.createNativeQuery(String.format("UPDATE posts_nonforeignkeyconstraint SET user_id = '%s' WHERE text = '%s'", userId, postText)).executeUpdate()
            )
                    .isInstanceOf(javax.persistence.PersistenceException.class).getCause().isInstanceOf(ConstraintViolationException.class);
        }
    }

    @Test(dependsOnMethods = {"shouldUpdateCreateUsersPerTenantsWithNativeQuery"}, dataProvider = "posts", testName = "should delete posts for tenant", description = "should delete posts for tenant")
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
