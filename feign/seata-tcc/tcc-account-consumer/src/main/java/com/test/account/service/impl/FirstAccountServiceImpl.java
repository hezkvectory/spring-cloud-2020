package com.test.account.service.impl;

import com.test.account.dao.AccountDao;
import com.test.account.entity.Account;
import com.test.account.service.FirstAccountService;
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
 * tcc事物要注意三点：
 * 1、空回滚
 * 2、幂等
 * 3、悬挂
 *
 * 解决办法，加本地事物消息表
 *
 *
 * 1、消息最终一致性
 * 2、最大努力通知
 * 3、消息事物表
 * 4、tcc
 */
@Service
@DubboService
public class FirstAccountServiceImpl implements FirstAccountService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private DataSource dataSource;

    /**
     * 初始化表结构和转账数据
     */
    @PostConstruct
    public void init() throws SQLException {
        int initAmount = 100;
        //初始化表结构
        prepareTable(dataSource);
        //初始化表数据
        prepareData(dataSource, "C", initAmount);
    }

    @Override
    @TwoPhaseBusinessAction(name = "firstTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepareMinus(BusinessActionContext businessActionContext, String userId, int amount) {
        //分布式事务ID
        final String xid = businessActionContext.getXid();

        return transactionTemplate.execute(status -> {
            try {
                //校验账户余额
                Account account = accountDao.getAccountForUpdate(userId);
                if (account == null) {
                    throw new RuntimeException("账户不存在");
                }
                if (account.getAmount() - account.getFreezedAmount() - amount < 0) {
                    throw new RuntimeException("余额不足");
                }
                //冻结转账金额
                int freezedAmount = account.getFreezedAmount() + amount;
                account.setFreezedAmount(freezedAmount);
                accountDao.updateFreezedAmount(account);
                System.out.println(String.format("prepareMinus account[%s] amount[%d], dtx transaction id: %s.", userId, amount, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });
    }

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
                //扣除账户余额
                int newAmount = account.getAmount() - money;
                if (newAmount < 0) {
                    throw new RuntimeException("余额不足");
                }
                account.setAmount(newAmount);
                //释放账户 冻结金额
                account.setFreezedAmount(account.getFreezedAmount() - money);
                accountDao.updateAmount(account);
                System.out.println(String.format("minus reduce account[%s] amount[%d], dtx transaction id: %s.", userId, money, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });
    }

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
                    //账户不存在，回滚什么都不做
                    return true;
                }
                if (account.getFreezedAmount() < money) {
                    //冻结金额小于预扣出金额，回滚什么也不做
                    return true;
                }
                //释放冻结金额
                account.setFreezedAmount(account.getFreezedAmount() - money);
                accountDao.updateFreezedAmount(account);
                System.out.println(String.format("Undo reduce userId[%s] money[%d], dtx transaction id: %s.", userId, money, xid));
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                status.setRollbackOnly();
                return false;
            }
        });
    }


    /**
     * 初始化账户数据
     *
     * @param dataSource
     * @param accountNo
     * @param amount
     */
    protected void prepareData(DataSource dataSource, String accountNo, int amount) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String sql = "insert into account(user_id, amount, freezed_amount) values(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountNo);
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

    /**
     * 初始化表结构
     *
     * @param dataSource
     */
    protected void prepareTable(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement s = conn.createStatement();
            try {
                s.execute("drop table business_activity");
            } catch (Exception e) {
            }
            s.execute("CREATE TABLE  business_activity  ( tx_id  varchar(128) NOT NULL , gmt_create  datetime not null , gmt_modified  datetime not null , instance_id  varchar(128)  , business_type  varchar(32) , business_id  varchar(128)  , state  varchar(2) , app_name  varchar(32) ,  timeout  int(11) ,  context  varchar(2000) , is_sync  varchar(1) , PRIMARY KEY ( tx_id )) ");
            System.out.println("创建 business_activity 表成功");

            try {
                s.execute("drop table business_action");
            } catch (Exception e) {
            }
            s.execute("CREATE TABLE  business_action  (  tx_id  varchar(128) , action_id  varchar(64)  , gmt_create  datetime not null , gmt_modified   datetime not null, instance_id  varchar(128)  , rs_id  varchar(128) , rs_desc  varchar(512) , rs_type  smallint(6)  , state  varchar(2) , context  varchar(2000) , PRIMARY KEY ( action_id ))");
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

}
