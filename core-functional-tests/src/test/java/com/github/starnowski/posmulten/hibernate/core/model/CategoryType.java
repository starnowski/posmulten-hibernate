package com.github.starnowski.posmulten.hibernate.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Dictionary table
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "category_types")
@NoArgsConstructor
public class CategoryType {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @ManyToMany
    private Set<CategoryTypeAttribute> attributes;

    @ManyToMany
    private List<CategoryTypeAttribute> attributesList;
}
