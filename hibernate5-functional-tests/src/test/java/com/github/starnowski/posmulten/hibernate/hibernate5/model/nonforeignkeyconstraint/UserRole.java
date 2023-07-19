package com.github.starnowski.posmulten.hibernate.hibernate5.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import com.github.starnowski.posmulten.hibernate.hibernate5.util.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_role_nonforeignkeyconstraint")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey")
@TenantTable
public class UserRole {
    @EmbeddedId
    @AttributeOverride(name="key", column=@Column(name="id"))
    @AttributeOverride(name="tenant", column=@Column(name = "tenant", insertable = false, updatable = false))
    private LongPrimaryKey primaryKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "tenant", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "user_id", referencedColumnName = "user_id"))
    })
    private User user;
}
