package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_info_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey")
@TenantTable(tenantIdColumn = "tenant")
@ToString(of = {"primaryKey", "username"})
public class User {

    @EmbeddedId
    @AttributeOverride(name = "stringKey", column = @Column(name = "user_id"))
    @AttributeOverride(name = "tenant", column = @Column(name = "tenant", insertable = false, updatable = false))
    private StringPrimaryKey primaryKey;
    private String username;
    private String password;

    @OneToMany(mappedBy = "author", fetch = LAZY)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name = "tenant", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private Set<Post> posts;
    @OneToMany(mappedBy = "user")
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name = "tenant", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private Set<UserRole> roles;
}
