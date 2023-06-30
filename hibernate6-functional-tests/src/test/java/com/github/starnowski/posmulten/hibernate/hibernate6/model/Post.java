package com.github.starnowski.posmulten.hibernate.hibernate6.model;

import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "posts")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TenantTable
@ToString(of = {"id", "text"})
public class Post {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User author;

    @Column(columnDefinition = "text")
    private String text;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @ManyToMany
    private Set<Category> categories;

    @ManyToMany
    private List<CategoryType> categoryTypes;
}
