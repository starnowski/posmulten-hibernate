package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;


import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

/**
 * Entity example with custom tenant column name "comment_tenant_id".
 * Column is not created by hibernate core but by posmulten-hibernate library.
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "comments_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey.primaryKey")
@TenantTable(tenantIdColumn = "comment_tenant_id")
public class Comment {
    @EmbeddedId
    @AttributeOverride(name="primaryKey", column=@Column(name="id"))
    @AttributeOverride(name="tenant", column=@Column(name = "comment_tenant_id", insertable = false, updatable = false))
    private PrimaryKey<Long> primaryKey;
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "comment_tenant_id", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private User author;
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "comment_tenant_id", referencedColumnName = "tenant_id")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "post_id", referencedColumnName = "key"))
    })
    private Post post;
    @Column(columnDefinition = "text")
    private String text;
}
