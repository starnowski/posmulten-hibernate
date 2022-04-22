package com.github.starnowski.posmulten.hibernate.core.context;

public class CurrentTenantContext {

    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> null);

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }
}
