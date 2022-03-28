package com.github.starnowski.posmulten.hibernate.core;

public class Properties {
    public static final int MAXIMUM_IDENTIFIER_LENGTH = 63;
    public static final String POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH = "hibernate.posmulten.maximum.identifier.length";
    public static final String GRANTEE = "hibernate.posmulten.grantee";
    public static final String ID_PROPERTY = "hibernate.posmulten.tenant.id.property";
    public static final String TENANT_ID_SET_CURRENT_AS_DEFAULT_FLAG = "hibernate.posmulten.tenant.id.set.current.as.default";
    public static final String TENANT_ID_INVALID_VALUES = "hibernate.posmulten.tenant.id.values.blacklist";
}
