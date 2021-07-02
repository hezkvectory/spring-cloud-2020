package com.sharding.commons.jdbc.datasource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass({ JdbcTemplate.class, PlatformTransactionManager.class})
@EnableConfigurationProperties(DataSourceProperties.class)
public class MeteorDataSourceTransactionManagerAutoConfiguration {


    private final DataSource dataSource;

    private final TransactionManagerCustomizers transactionManagerCustomizers;

    public MeteorDataSourceTransactionManagerAutoConfiguration(DataSource dataSource,
                                              ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        this.dataSource = dataSource;
        this.transactionManagerCustomizers = transactionManagerCustomizers.getIfAvailable();
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    @ConditionalOnBean(DataSource.class)
    public DataSourceTransactionManager meteorTransactionManager(DataSourceProperties properties) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource);
        if (this.transactionManagerCustomizers != null) {
            this.transactionManagerCustomizers.customize(transactionManager);
        }
        return transactionManager;
    }

}
