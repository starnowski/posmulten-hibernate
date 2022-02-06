package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.postgresql.core.common.SQLDefinition;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
import org.hibernate.tool.schema.spi.ScriptSourceInput;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SharedSchemaContextSourceInput implements ScriptSourceInput {

    private final ISharedSchemaContext context;
    List<SQLDefinition> definitions;

    public SharedSchemaContextSourceInput(ISharedSchemaContext context) {
        this.context = context;
    }

    @Override
    public void prepare() {
        this.definitions = context.getSqlDefinitions();
    }

    @Override
    public List<String> read(ImportSqlCommandExtractor importSqlCommandExtractor) {
        return this.definitions.stream().flatMap(definition -> Stream.of(importSqlCommandExtractor.extractCommands(new StringReader(definition.getCreateScript())))).collect(toList());
    }

    @Override
    public void release() {
        this.definitions = null;
    }
}
