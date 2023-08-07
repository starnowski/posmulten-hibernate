package com.github.starnowski.posmulten.hibernate.common.context;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.common.Properties.DEFAULT_TENANT_ID;

public class PropertiesHibernateContextFactory {

    public HibernateContext build(Map map) {
        HibernateContext.HibernateContextBuilder builder = HibernateContext.builder();
        if (map != null) {
            if (map.containsKey(DEFAULT_TENANT_ID)) {
                builder.withDefaultTenantId((String) map.get(DEFAULT_TENANT_ID));
            }
        }
        return builder.build();
    }
}
