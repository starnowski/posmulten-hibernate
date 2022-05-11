package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_info")
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryKey")
@TenantTable(tenantIdColumn = "tenant")
public class User {

    @EmbeddedId
    @AttributeOverride(name="primaryKey", column=@Column(name="user_id"))
    @AttributeOverride(name="tenant", column=@Column(name = "tenant", insertable = false, updatable = false))
    private StringPrimaryKey primaryKey;
    private String username;
    private String password;
//    @OneToMany(mappedBy = "user")
//    private Set<UserRole> roles;
}
