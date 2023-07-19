package com.github.starnowski.posmulten.hibernate.hibernate5.connections;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CurrentTenantPreparedStatementSetterForLong implements ICurrentTenantPreparedStatementSetter{
    @Override
    public void setup(PreparedStatement statement, String tenant) throws SQLException {
        statement.setLong(1, Long.parseLong(tenant));
    }
}
