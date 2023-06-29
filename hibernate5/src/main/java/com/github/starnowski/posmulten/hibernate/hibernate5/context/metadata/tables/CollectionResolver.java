package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables;

import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Table;

import java.util.Optional;

public class CollectionResolver {

    public Collection resolve(Metadata metadata, Table table) {
        Optional<Collection> optional = metadata.getCollectionBindings().stream().filter(collection -> table.equals(collection.getCollectionTable())).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }
}
