package com.github.starnowski.posmulten.hibernate.core;

public class Properties {
    public static final int MAXIMUM_IDENTIFIER_LENGTH = 63;
    public static final String POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH = "hibernate.posmulten.maximum.identifier.length";
    public static final String GRANTEE = "hibernate.posmulten.grantee";
    public static final String ID_PROPERTY = "hibernate.posmulten.tenant.id.property";
    public static final String TENANT_ID_SET_CURRENT_AS_DEFAULT_FLAG = "hibernate.posmulten.tenant.id.set.current.as.default";
    public static final String TENANT_ID_INVALID_VALUES = "hibernate.posmulten.tenant.id.values.blacklist";
    public static final String TENANT_ID_DUMMY_VALUE = "hibernate.posmulten.tenant.id.value.dummy";
    public static final String TENANT_COLUMN_JAVA_TYPE = "hibernate.posmulten.tenant.column.java.type";
    public static final String TENANT_COLUMN_JAVA_TYPE_CUSTOM_RESOLVER = "hibernate.posmulten.tenant.column.java.type.custom.resolver";
    public static final String SCHEMA_BUILDER_PROVIDER = "hibernate.posmulten.schema.builder.provider";
    public static final String GET_CURRENT_TENANT_FUNCTION_NAME = "hibernate.posmulten.function.getcurrenttenant.name";
    public static final String SET_CURRENT_TENANT_FUNCTION_NAME = "hibernate.posmulten.function.setcurrenttenant.name";
    public static final String EQUALS_CURRENT_TENANT_IDENTIFIER_FUNCTION_NAME = "hibernate.posmulten.function.equalscurrenttenantidentifier.name";
    public static final String TENANT_HAS_AUTHORITIES_FUNCTION_NAME = "hibernate.posmulten.function.tenanthasauthorities.name";
    //TODO
    public static final String METADATA_TABLE_ADDITIONAL_ENRICHERS = "hibernate.posmulten.metadata.table.additional.enrichers";
    public static final String METADATA_ADDITIONAL_ENRICHERS = "hibernate.posmulten.metadata.additional.enrichers";
    public static final String IGNORE_FOREIGN_KEY_CONSTRAINT = "hibernate.posmulten.foreignkey.constraint.ignore";
}
