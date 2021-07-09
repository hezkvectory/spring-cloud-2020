package com.test.account.service.impl;

import com.test.account.dao.AccountDao;
import com.test.account.entity.Account;
import com.test.account.service.SecondAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 加钱参与者实现
 */
@Service
@DubboService
public class SecondAccountImpl implements SecondAccountService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void init() throws SQLException {
        int initAmount = 100;
        //初始化表结构
        prepareTable(dataSource);
        //初始化表数据
        prepareData(dataSource, "A", initAmount);
        prepareData(dataSource, "B", initAmount);
    }

    /**
     * 一阶段准备，转入资金 准备
     */
    @Override
    @TwoPhaseBusinessAction(name = "secondTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepareAdd(final BusinessActionContext businessActionContext, final String userId, final int amount) {
        //分布式事务ID
        final String xid = businessActionContext.getXid();

        return transactionTemplate.execute(status -> {
            try {
                //校验账户
                Account account = accountDao.getAccountForUpdate(userId);
                if (account == null) {
                    System.out.println("prepareAdd: 账户[" + userId + "]不存在, txId:" + businessActionContext.getXid());
                    return false;
                }
                //待转入资金作为 不可用金额
                int freezedAmount = account.getFreezedAmount() + amount;
                account.setFreezedAmount(freezedAmount);
                accountDao.updateFreezedAmount(account);
                System.out.println(String.format("prepareAdd account[%s] amount[%d], dtx transaction id: %s.", userId, amount, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });
    }

    /**
     * 二阶段提交
     *
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        //分布式事务ID
        final String xid = businessActionContext.getXid();
        //账户ID
        final String userId = String.valueOf(businessActionContext.getActionContext("userId"));
        //转出金额
        final int money = Integer.valueOf(String.valueOf(businessActionContext.getActionContext("money")));
        return transactionTemplate.execute(status -> {
            try {
                Account account = accountDao.getAccountForUpdate(userId);
                //加钱
                int newAmount = account.getAmount() + money;
                account.setAmount(newAmount);
                //冻结金额 清除
                account.setFreezedAmount(account.getFreezedAmount() - money);
                accountDao.updateAmount(account);

                System.out.println(String.format("add userId[%s] amount[%d], dtx transaction id: %s.", userId, money, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });

    }

    /**
     * 二阶段回滚
     *
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        //分布式事务ID
        final String xid = businessActionContext.getXid();
        //账户ID
        final String userId = String.valueOf(businessActionContext.getActionContext("userId"));
        //转出金额
        final int money = Integer.valueOf(String.valueOf(businessActionContext.getActionContext("money")));
        return transactionTemplate.execute(status -> {
            try {
                Account account = accountDao.getAccountForUpdate(userId);
                if (account == null) {
                    //账户不存在, 无需回滚动作
                    return true;
                }
                if (account.getFreezedAmount() < money) {
                    //冻结金额小于预扣出金额，回滚什么也不做
                    return true;
                }
                //冻结金额 清除
                account.setFreezedAmount(account.getFreezedAmount() - money);
                accountDao.updateFreezedAmount(account);

                System.out.println(String.format("Undo prepareAdd userId[%s] money[%d], dtx transaction id: %s.", userId, money, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });
    }


    protected void prepareTable(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement s = conn.createStatement();
            try {
                s.execute("drop table business_activity");
            } catch (Exception e) {
            }
            s.execute("CREATE TABLE  business_activity  ( tx_id  varchar(128) NOT NULL , gmt_create  datetime not null , gmt_modified   datetime not null , instance_id  varchar(128)  , business_type  varchar(32) , business_id  varchar(128)  , state  varchar(2) , app_name  varchar(32) ,  timeout  int(11) ,  context  varchar(2000) , is_sync  varchar(1) , PRIMARY KEY ( tx_id )) ");
            System.out.println("创建 business_activity 表成功");

            try {
                s.execute("drop table business_action");
            } catch (Exception e) {
            }
            s.execute("CREATE TABLE  business_action  (  tx_id  varchar(128) , action_id  varchar(64)  , gmt_create   datetime not null  , gmt_modified   datetime not null , instance_id  varchar(128)  , rs_id  varchar(128) , rs_desc  varchar(512) , rs_type  smallint(6)  , state  varchar(2) , context  varchar(2000) , PRIMARY KEY ( action_id ))");
            System.out.println("创建 business_action 表成功");

            //账户余额表
            try {
                s.execute("drop table account");
            } catch (Exception e) {
            }
            s.execute("create table account(user_id varchar(256), amount int,  freezed_amount DOUBLE, PRIMARY KEY (user_id))");
            System.out.println("创建account表成功");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化账户数据
     */
    protected void prepareData(DataSource dataSource, String userId, int amount) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String sql = "insert into account(user_id, amount, freezed_amount) values(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setDouble(2, amount);
            ps.setDouble(3, 0);
            ps.executeUpdate();
            ps.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

}
