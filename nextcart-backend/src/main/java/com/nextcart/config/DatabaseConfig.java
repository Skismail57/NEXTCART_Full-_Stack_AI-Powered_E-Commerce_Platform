package com.nextcart.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream inputStream = DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            props.load(inputStream);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver"));

            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximum-pool-size")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimum-idle")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("hikari.connection-timeout")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("hikari.idle-timeout")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("hikari.max-lifetime")));

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            logger.info("Database connection pool initialized successfully");

        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Database connection pool closed");
        }
    }
}
