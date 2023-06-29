package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables;

import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

import java.util.Optional;

public class PersistentClassResolver {

    public PersistentClass resolve(Metadata metadata, Table table)
    {
        if (!table.isPhysicalTable()) {
            return null;
        }
        Optional<PersistentClass> pClass = metadata.getEntityBindings().stream().filter(persistentClass -> table.equals(persistentClass.getTable())).findFirst();
        if (!pClass.isPresent()) {
            return null;
        }
        return pClass.get();
    }
}
