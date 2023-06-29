package com.github.starnowski.posmulten.hibernate.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TenantTable {

    String tenantIdColumn() default "";

    boolean skipAddingColumnIfColumnNotExists() default false;
}
