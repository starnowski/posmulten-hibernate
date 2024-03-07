--TODO Add option in builder component so that the correct access for grantee could be added.
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO "posmhib4-user";
--TODO Add option in builder component so that the correct access for grantee could be added.
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO "posmhib4-user";

-- This is required because hibernate does not creates foreign key constraint for JoinColumnsOrFormulas annotation
ALTER TABLE posts_nonforeignkeyconstraint ADD CONSTRAINT fk_posts_users_author_manual_added FOREIGN KEY (user_id, tenant_id) REFERENCES user_info_nonforeignkeyconstraint(user_id, tenant);