package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "posts_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey.primaryKey")
@TenantTable
public class Post {

    @EmbeddedId
    @AttributeOverride(name="primaryKey", column=@Column(name="id"))
    @AttributeOverride(name="tenant", column=@Column(name = "tenant", insertable = false, updatable = false))
    private PrimaryKey<Long> primaryKey;
//    @ManyToOne
//    @MapsId("tenant")
//    @JoinColumn(name = "user_id")
//    private User author;

    @Column(columnDefinition = "text")
    private String text;

//    @OneToMany(mappedBy = "post")
//    private Set<Comment> comments;

    @ManyToMany
    private Set<Category> categories;
}
