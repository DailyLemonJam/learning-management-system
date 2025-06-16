package com.leverx.learningmanagementsystem.multitenancy.tenant.context;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> CURRENT_TENANT_SUBDOMAIN = new ThreadLocal<>();

    public static void setTenant(String tenantId, String subdomain) {
        CURRENT_TENANT_ID.set(tenantId);
        CURRENT_TENANT_SUBDOMAIN.set(subdomain);
    }

    public static String getTenantId() {
        return CURRENT_TENANT_ID.get();
    }

    public static String getTenantSubdomain() {
        return CURRENT_TENANT_SUBDOMAIN.get();
    }

    public static void clear() {
        CURRENT_TENANT_ID.remove();
        CURRENT_TENANT_SUBDOMAIN.remove();
    }
}
