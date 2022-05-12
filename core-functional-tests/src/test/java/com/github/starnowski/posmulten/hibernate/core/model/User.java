package com.github.starnowski.posmulten.hibernate.core.model;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_info")
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
@TenantTable
@ToString(of = {"userId", "username"})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles;
    @OneToMany(mappedBy = "author")
    private Set<Post> posts;
}
