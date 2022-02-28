package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import java.util.HashMap;
import java.util.Map;

public class TenantTableProperties {
    private String table;
    private Map<String, String> primaryKeysColumnAndTypeMap = new HashMap<>();
    private String tenantColumnName;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, String> getPrimaryKeysColumnAndTypeMap() {
        return primaryKeysColumnAndTypeMap;
    }

    public void setPrimaryKeysColumnAndTypeMap(Map<String, String> primaryKeysColumnAndTypeMap) {
        this.primaryKeysColumnAndTypeMap = primaryKeysColumnAndTypeMap;
    }

    public String getTenantColumnName() {
        return tenantColumnName;
    }

    public void setTenantColumnName(String tenantColumnName) {
        this.tenantColumnName = tenantColumnName;
    }
}
