package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity example with custom tenant column name "categoryTenantId".
 * Column is created by hibernate core.
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
    @AttributeOverride(name="primaryKey", column=@Column(name="id"))
    @AttributeOverride(name="tenant", column=@Column(name = "categoryTenantId", insertable = false, updatable = false))
    private PrimaryKey<Long> primaryKey;

    @Column
    private String text;

    //TODO
//    @ManyToMany(mappedBy = "categories")
//    private Set<Post> posts;
}
