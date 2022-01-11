package com.github.starnowski.posmulten.hibernate.core.schema;

import org.hibernate.tool.schema.internal.HibernateSchemaManagementTool;
import org.hibernate.tool.schema.spi.SchemaCreator;

import java.util.Map;

public class PosmultenSchemaManagementTool extends HibernateSchemaManagementTool {

    @Override
    public SchemaCreator getSchemaCreator(Map options) {
        return new SchemaCreatorWrapper(super.getSchemaCreator(options), getServiceRegistry());
    }
}
