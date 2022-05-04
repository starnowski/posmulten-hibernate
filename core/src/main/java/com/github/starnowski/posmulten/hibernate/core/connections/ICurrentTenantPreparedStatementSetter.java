package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.service.Service;

import java.sql.PreparedStatement;

public interface ICurrentTenantPreparedStatementSetter extends Service {

    void setup(PreparedStatement statement, String tenant);
}
