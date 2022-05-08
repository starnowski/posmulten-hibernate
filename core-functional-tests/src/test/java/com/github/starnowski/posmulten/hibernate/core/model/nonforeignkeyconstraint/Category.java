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
@Table(name = "categories")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TenantTable(tenantIdColumn = "categoryTenantId")
public class Category {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String text;

    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts;

    @Column(name = "categoryTenantId", insertable = false, updatable = false)
    private String tenantId;
}
