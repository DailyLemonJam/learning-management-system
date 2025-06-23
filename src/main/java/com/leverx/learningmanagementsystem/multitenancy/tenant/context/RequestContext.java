package com.leverx.learningmanagementsystem.multitenancy.tenant.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private static final ThreadLocal<Map<String, String>> CONTEXT = ThreadLocal.withInitial(HashMap::new);

    public static final String TENANT_ID = "tenantId";
    public static final String SUBDOMAIN = "subdomain";

    public static void setTenant(String tenantId, String subdomain) {
        var map = new HashMap<String, String>();

        map.put(TENANT_ID, tenantId);
        map.put(SUBDOMAIN, subdomain);

        CONTEXT.set(map);
    }

    public static String getTenantId() {
        return CONTEXT.get().get(TENANT_ID);
    }

    public static String getTenantSubdomain() {
        return CONTEXT.get().get(SUBDOMAIN);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
