package com.github.starnowski.posmulten.hibernate.hibernate5.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity example with custom tenant column name "categoryTenantId".
 * Column is created by hibernate hibernate5.
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "categories_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey.primaryKey")
@TenantTable(tenantIdColumn = "categoryTenantId")
public class Category {

    @EmbeddedId
    @AttributeOverride(name = "primaryKey", column = @Column(name = "id"))
    @AttributeOverride(name = "tenant", column = @Column(name = "categoryTenantId", insertable = false, updatable = false))
    private PrimaryKey<Long> primaryKey;

    @Column
    private String text;

    @ManyToMany
    @JoinTable(name = "posts_nonforeignkeyconstraint_categories_nonforeignkeyconstraint")
    private Set<Post> posts;
}
