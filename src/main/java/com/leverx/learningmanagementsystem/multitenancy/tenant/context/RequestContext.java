package com.leverx.learningmanagementsystem.multitenancy.tenant.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private static final ThreadLocal<Map<String, String>> CONTEXT = ThreadLocal.withInitial(HashMap::new);

    public static final String TENANT_ID = "tenantId";
    public static final String SUBDOMAIN = "subdomain";
    public static final String USERNAME = "username";

    public static void setTenantId(String tenantId) {
        CONTEXT.get().put(TENANT_ID, tenantId);
    }

    public static void setSubdomain(String subdomain) {
        CONTEXT.get().put(SUBDOMAIN, subdomain);
    }

    public static void setUsername(String username) {
        CONTEXT.get().put(USERNAME, username);
    }

    public static String getTenantId() {
        return CONTEXT.get().get(TENANT_ID);
    }

    public static String getSubdomain() {
        return CONTEXT.get().get(SUBDOMAIN);
    }

    public static String getUsername() {
        return CONTEXT.get().get(USERNAME);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
