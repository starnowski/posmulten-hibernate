package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;

public interface IDefaultSharedSchemaContextBuilderProvider {

    DefaultSharedSchemaContextBuilder get();
}
