package com.github.starnowski.posmulten.hibernate.hibernate6.model.nonforeignkeyconstraint;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class LongPrimaryKey implements Serializable {

    private Long key;
    private String tenant;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}