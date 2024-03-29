package com.github.starnowski.posmulten.hibernate.hibernate5.model;

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
    @JoinTable(name = "category_types_category_type_attributes_list")
    private List<CategoryTypeAttribute> attributesList;
}
