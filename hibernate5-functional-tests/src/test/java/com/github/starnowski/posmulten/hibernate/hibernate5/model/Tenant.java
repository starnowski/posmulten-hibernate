package com.github.starnowski.posmulten.hibernate.hibernate5.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "tenant_info")
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Tenant {

    @Id
    private String name;
}
