package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.service.Service;

public interface IDefaultSharedSchemaContextBuilderProvider extends Service {

    DefaultSharedSchemaContextBuilder get();
}
