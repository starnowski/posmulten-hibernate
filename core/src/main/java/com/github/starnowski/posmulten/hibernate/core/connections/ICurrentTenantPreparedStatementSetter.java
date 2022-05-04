package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.service.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ICurrentTenantPreparedStatementSetter extends Service {

    void setup(PreparedStatement statement, String tenant) throws SQLException;
}
