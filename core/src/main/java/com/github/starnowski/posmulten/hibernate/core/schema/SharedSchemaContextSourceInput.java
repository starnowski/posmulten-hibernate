package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
import org.hibernate.tool.schema.spi.ScriptSourceInput;

import java.util.List;

public class SharedSchemaContextSourceInput implements ScriptSourceInput {

    private final ISharedSchemaContext context;

    public SharedSchemaContextSourceInput(ISharedSchemaContext context) {
        this.context = context;
    }

    @Override
    public void prepare() {

    }

    @Override
    public List<String> read(ImportSqlCommandExtractor importSqlCommandExtractor) {
        return null;
    }

    @Override
    public void release() {

    }
}
