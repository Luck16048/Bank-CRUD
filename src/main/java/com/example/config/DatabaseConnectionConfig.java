package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


public class DatabaseConnectionConfig {

    private static final String URL = System.getenv("URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public static void migrate() {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }

    public static DSLContext getDSL() {
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
}
