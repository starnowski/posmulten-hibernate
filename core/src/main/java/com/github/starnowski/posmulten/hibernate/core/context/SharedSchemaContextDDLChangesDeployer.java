package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.tool.schema.internal.exec.GenerationTarget;

public class SharedSchemaContextDDLChangesDeployer {

    public void deploy(DefaultSharedSchemaContextBuilder builder, GenerationTarget[] targets){}

    private static void applySqlStrings(GenerationTarget[] targets, String... sqlStrings) {
        if (sqlStrings == null) {
            return;
        }

        for (GenerationTarget target : targets) {
            for (String sqlString : sqlStrings) {
                target.accept(sqlString);
            }
        }
    }
}
