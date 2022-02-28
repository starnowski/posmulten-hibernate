package com.github.starnowski.posmulten.hibernate.test.utils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class ReflectionUtils {

    public static boolean classContainsProperty(Class clazz, String propertyName)
    {
        Field[] fields = clazz.getFields();
        return Stream.of(fields).anyMatch(field -> field.getName().equals(propertyName));
    }
}
