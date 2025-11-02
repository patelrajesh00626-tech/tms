package com.translation.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

/**
 * Hibernate StatementInspector implementation for logging or modifying SQL queries.
 * This class can be used to inspect and potentially modify SQL statements before they are executed.
 */
@Component
public class QueryInspector implements StatementInspector {
    
    @Override
    public String inspect(String sql) {
        // You can add logging or modify the SQL here if needed
        // For example, to log all SQL queries:
        // System.out.println("Executing SQL: " + sql);
        return sql; // Return the original SQL unchanged
    }
}
