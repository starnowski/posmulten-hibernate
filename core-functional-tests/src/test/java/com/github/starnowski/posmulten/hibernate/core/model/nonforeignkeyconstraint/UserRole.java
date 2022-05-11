package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import com.github.starnowski.posmulten.hibernate.core.util.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_role")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey.primaryKey")
@TenantTable
public class UserRole {
    @EmbeddedId
    @AttributeOverride(name="primaryKey", column=@Column(name="id"))
    @AttributeOverride(name="tenant", column=@Column(name = "tenant", insertable = false, updatable = false))
    private LongPrimaryKey primaryKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @ManyToOne
    @MapsId("tenant")
    @JoinColumns(value = {
            @JoinColumn(name = "tenant", referencedColumnName = "tenant"),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
    private User user;
}
