package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "posts_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
@ToString(of = {"key", "text"})
@TenantTable(tenantIdColumn = "tenant")
@IdClass(LongPrimaryKey.class)
public class Post {

    @Id
    @GeneratedValue
    private long key;
    @Id
    @Column(name = "tenant", insertable = false, updatable = false)
    private String tenant;

    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "tenant", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private User author;

    @Column(columnDefinition = "text")
    private String text;

//    @OneToMany(mappedBy = "post")
//    private Set<Comment> comments;

    @ManyToMany
    private Set<Category> categories;
}
