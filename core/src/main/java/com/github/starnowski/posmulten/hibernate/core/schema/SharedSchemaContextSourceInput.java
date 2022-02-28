package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.postgresql.core.common.SQLDefinition;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
import org.hibernate.tool.schema.spi.ScriptSourceInput;

import java.util.List;
import java.util.stream.Collectors;

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
        /*
         * Temporary implementation does not use importSqlCommandExtractor component until the below issues are going to be resolved.
         */
//        https://github.com/starnowski/posmulten/issues/233
//        https://github.com/starnowski/posmulten/issues/234
//        String script = this.definitions.stream().map(definition -> definition.getCreateScript()).collect(Collectors.joining());
//        return this.definitions.stream().flatMap(definition -> Stream.of(importSqlCommandExtractor.extractCommands(new StringReader(definition.getCreateScript())))).collect(toList());
        return this.definitions.stream().map(definition -> definition.getCreateScript()).collect(Collectors.toList());
    }

    @Override
    public void release() {
        this.definitions = null;
    }
}
