package com.github.starnowski.posmulten.hibernate.test.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class TestUtils {

    public static boolean isFunctionExists(Statement statement, String functionName, String schema) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT 1 FROM pg_proc pg, pg_catalog.pg_namespace pgn WHERE ");
        sb.append("pg.proname = '");
        sb.append(functionName);
        sb.append("' AND ");
        if (schema == null) {
            sb.append("pgn.nspname = 'public'");
        } else {
            sb.append("pgn.nspname = '");
            sb.append(schema);
            sb.append("'");
        }
        sb.append(" AND ");
        sb.append("pg.pronamespace =  pgn.oid");
        return isAnyRecordExists(statement, sb.toString());
    }

    public static boolean isAnyRecordExists(Statement statement, final String sql) throws SQLException {
        return statement.executeQuery(sql).isBeforeFirst();
    }

    public static String statementThatReturnsColumnNameAndType(String table, String column, String schema, String database) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT(column_name || ' ' || data_type , CASE character_maximum_length WHEN NULL THEN '' ELSE '(' || character_maximum_length || ')' END ) FROM information_schema.columns WHERE ");
        //TODO Database name
        sb.append("table_catalog = '");
        sb.append(database);
        sb.append("' AND ");
        if (schema == null) {
            sb.append("table_schema = 'public'");
        } else {
            sb.append("table_schema = '");
            sb.append(schema);
            sb.append("'");
        }
        sb.append(" AND ");
        sb.append("table_name = '");
        sb.append(table);
        sb.append("' AND ");
        sb.append("column_name = '");
        sb.append(column);
        sb.append("'");
        return sb.toString();
    }

    public static boolean isTableExists(Statement statement, String tableName, String schema) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT 1 FROM pg_catalog.pg_tables WHERE ");
        sb.append("tablename = '");
        sb.append(tableName);
        sb.append("' AND ");
        if (schema == null) {
            sb.append("schemaname = 'public'");
        } else {
            sb.append("schemaname = '");
            sb.append(schema);
            sb.append("'");
        }
        return isAnyRecordExists(statement, sb.toString());
    }

    public static boolean isConstraintExists(Statement statement, String schema, String table, String constraintName) throws SQLException {
        String template = "SELECT 1\n" +
                "\t\tFROM information_schema.table_constraints\n" +
                "\t\tWHERE table_schema = '%s' AND table_name = '%s' AND constraint_name = '%s'";
        String selectStatement = format(template, schema == null ? "public" : schema, table, constraintName);
        return isAnyRecordExists(statement, selectStatement);
    }

    public static String selectAndReturnFirstRecordAsString(Statement statement, final String sql) throws SQLException {
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }

    public static Long selectAndReturnFirstRecordAsLong(Statement statement, final String sql) throws SQLException {
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getLong(1);
    }
}
