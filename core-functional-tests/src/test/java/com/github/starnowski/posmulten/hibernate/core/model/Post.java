package com.github.starnowski.posmulten.hibernate.core.model;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
