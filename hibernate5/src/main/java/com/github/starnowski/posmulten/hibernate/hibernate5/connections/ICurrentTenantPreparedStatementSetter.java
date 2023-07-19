package com.github.starnowski.posmulten.hibernate.hibernate5.connections;

import org.hibernate.service.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Sets the value for the first parameter of prepared statement {@link PreparedStatement} that represent the value of the current tenant.
 */
public interface ICurrentTenantPreparedStatementSetter extends Service {

    /**
     * The method sets the value for the first parameter "1" of the prepared statement {@link PreparedStatement} that represent the value of the current tenant.
     * @param statement prepared statement
     * @param tenant current tenant value
     * @throws SQLException any exception thrown by method invoked for a prepared statement object
     */
    void setup(PreparedStatement statement, String tenant) throws SQLException;
}
