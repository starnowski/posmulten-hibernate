package com.github.starnowski.posmulten.hibernate.hibernate5.model;

import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import com.github.starnowski.posmulten.hibernate.hibernate5.util.RoleEnum;
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
@EqualsAndHashCode(of = "id")
@TenantTable
public class UserRole {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
