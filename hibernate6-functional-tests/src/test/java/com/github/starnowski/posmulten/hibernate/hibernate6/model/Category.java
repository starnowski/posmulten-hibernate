package com.github.starnowski.posmulten.hibernate.hibernate6.model;

import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * Entity example with custom tenant column name "categoryTenantId".
 * Column is created by hibernate hibernate5.
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "categories")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TenantTable(tenantIdColumn = "categorytenantid")
public class Category {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String text;

    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts;

    @Column(name = "categorytenantid", insertable = false, updatable = false)
    private String tenantId;

    @ManyToMany
    private Set<CategoryType> categoryTypes;
}
