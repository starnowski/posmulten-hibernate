package com.github.starnowski.posmulten.hibernate.hibernate6.model.nonforeignkeyconstraint;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
