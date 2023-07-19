package com.github.starnowski.posmulten.hibernate.hibernate5.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Dictionary table
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "category_type_attributes")
@NoArgsConstructor
public class CategoryTypeAttribute {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String value;
}
