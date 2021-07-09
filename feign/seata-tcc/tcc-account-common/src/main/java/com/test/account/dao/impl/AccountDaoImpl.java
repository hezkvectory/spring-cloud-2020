package com.test.account.dao.impl;

import com.test.account.dao.AccountDao;
import com.test.account.entity.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    public SqlSessionTemplate sqlSession;

    @Override
    public void addAccount(Account account) throws SQLException {
        sqlSession.insert("addAccount", account);
    }

    @Override
    public int updateAmount(Account account) throws SQLException {
        return sqlSession.update("updateAmount", account);
    }

    @Override
    public Account getAccount(String accountNo) throws SQLException {
        return (Account) sqlSession.selectOne("getAccount", accountNo);
    }

    @Override
    public Account getAccountForUpdate(String accountNo) throws SQLException {
        return (Account) sqlSession.selectOne("getAccountForUpdate", accountNo);
    }

    @Override
    public int updateFreezedAmount(Account account) throws SQLException {
        return sqlSession.update("updateFreezedAmount", account);
    }

    @Override
    public void deleteAllAccount() throws SQLException {
        sqlSession.update("deleteAllAccount");
    }

}
