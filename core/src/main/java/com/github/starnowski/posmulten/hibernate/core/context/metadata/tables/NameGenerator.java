package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import org.hibernate.mapping.Table;

import static java.util.Collections.emptyList;
import static org.hibernate.mapping.Constraint.generateName;

public class NameGenerator {

    private final int maxLength;

    public NameGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public String generate(String prefix, Table table) {
        return generateName(prefix, table, emptyList()).substring(0, maxLength);
    }
}
