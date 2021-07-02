package com.sharding.commons.jdbc.datasource;

import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 结合sharding-jdbc，使用@Transactional强制让被wrapper的方法，走主库
 */
public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {

    public DataSourceTransactionManager() {
        super();
    }

    public DataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void prepareTransactionalConnection(Connection con, TransactionDefinition definition) throws SQLException {
        super.prepareTransactionalConnection(con, definition);
        if (!isEnforceReadOnly() && !definition.isReadOnly()) {
            HintManager.getInstance().setMasterRouteOnly();
//            con.setReadOnly(false); //sharding-jdbc没有处理conn.readonly? 根据hint和sql type进行判断走主库还是从库
        }
    }
}
