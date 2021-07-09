package com.test.account.dao;


import com.test.account.entity.Account;

import java.sql.SQLException;

/**
 * 余额账户 DAO
 */
public interface AccountDao {

    void addAccount(Account account) throws SQLException;
    
    int updateAmount(Account account) throws SQLException;
    
    int updateFreezedAmount(Account account) throws SQLException;
    
    Account getAccount(String userId) throws SQLException;
    
    Account getAccountForUpdate(String userId) throws SQLException;
    
    void deleteAllAccount() throws SQLException;
}
