package com.github.starnowski.posmulten.hibernate.hibernate5.model.nonforeignkeyconstraint;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class StringPrimaryKey implements Serializable {

    private String stringKey;

    private String tenant;

    public String getStringKey() {
        return stringKey;
    }


    public void setStringKey(String stringKey) {
        this.stringKey = stringKey;
    }

    public String getTenant() {
        return tenant;
    }


    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public StringPrimaryKey withStringKey(String stringKey) {
        this.stringKey = stringKey;
        return this;
    }

    public StringPrimaryKey withTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }
}