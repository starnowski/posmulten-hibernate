package com.github.starnowski.posmulten.hibernate.common.context;

public class HibernateContext {

    private final String defaultTenantId;

    public HibernateContext(String defaultTenantId) {
        this.defaultTenantId = defaultTenantId;
    }

    public static HibernateContextBuilder builder()
    {
        return new HibernateContextBuilder();
    }

    public static class HibernateContextBuilder {
        private String defaultTenantId;

        public String getDefaultTenantId() {
            return defaultTenantId;
        }

        public HibernateContextBuilder withDefaultTenantId(String defaultTenantId) {
            this.defaultTenantId = defaultTenantId;
            return this;
        }

        public HibernateContextBuilder hibernateContext(HibernateContext hibernateContext) {
            return withDefaultTenantId(hibernateContext.defaultTenantId);
        }

        public HibernateContext build() {
            return new HibernateContext(defaultTenantId);
        }
    }
}
