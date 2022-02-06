package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.service.Service;
import org.hibernate.tool.schema.spi.ScriptSourceInput;
import org.hibernate.tool.schema.spi.SourceDescriptor;

import java.util.Map;

public class SourceDescriptorFactory implements Service {

    public SourceDescriptor build(ISharedSchemaContext context, SourceDescriptor sourceDescriptor, Map configurationValues) {
        return null;
    }
}
