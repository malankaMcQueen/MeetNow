package com.example.meetnow.configuration;

import com.example.meetnow.service.util.JsonUtils;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.jackson2.Jackson2Config;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Конфигурация JDBI.
 */
@Configuration
public class CommonJdbiConfig {

    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin()).installPlugin(new PostgresPlugin())
                .installPlugin(new Jackson2Plugin());
        jdbi.getConfig(Jackson2Config.class).setMapper(JsonUtils.OBJECT_MAPPER);
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        return jdbi;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}