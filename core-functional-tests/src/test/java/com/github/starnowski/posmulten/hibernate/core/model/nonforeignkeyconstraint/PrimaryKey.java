package com.github.starnowski.posmulten.hibernate.core.model.nonforeignkeyconstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class PrimaryKey<K> implements Serializable {

    K primaryKey;
    String tenant;

    public K getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(K primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
