package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.hibernate.mapping.Constraint.generateName;

public class NameGenerator {

    private final int maxLength;

    public NameGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public String generate(String prefix, Table table) {
        return generate(prefix, table, null);
    }

    public String generate(String prefix, Table table, List<Column> columns) {
        List<Column> sortedColumnsByName = ofNullable(columns).orElseGet(Collections::emptyList).stream().sorted(Comparator.comparing(Column::getName)).collect(toList());
        String generatedName = generateName(prefix, table, sortedColumnsByName);
        return generatedName.length() > maxLength ? generatedName.substring(0, maxLength) : generatedName;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
