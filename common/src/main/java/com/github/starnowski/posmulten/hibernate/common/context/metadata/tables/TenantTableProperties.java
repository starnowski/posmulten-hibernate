package com.github.starnowski.posmulten.hibernate.common.context.metadata.tables;

import java.util.HashMap;
import java.util.Map;

public class TenantTableProperties {
    private String table;
    private String schema;
    private Map<String, String> primaryKeysColumnAndTypeMap;
    private String tenantColumnName;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, String> getPrimaryKeysColumnAndTypeMap() {
        return primaryKeysColumnAndTypeMap == null ? new HashMap<>() : new HashMap<>(primaryKeysColumnAndTypeMap);
    }

    public void setPrimaryKeysColumnAndTypeMap(Map<String, String> primaryKeysColumnAndTypeMap) {
        this.primaryKeysColumnAndTypeMap = new HashMap<>(primaryKeysColumnAndTypeMap);
    }

    public String getTenantColumnName() {
        return tenantColumnName;
    }

    public void setTenantColumnName(String tenantColumnName) {
        this.tenantColumnName = tenantColumnName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
