package com.test.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface FirstAccountService {

    /**
     * 一阶段方法
     */
    @TwoPhaseBusinessAction(name = "firstTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepareMinus(BusinessActionContext businessActionContext,
                         @BusinessActionContextParameter(paramName = "userId") String userId,
                         @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 二阶段提交
     */
    boolean commit(BusinessActionContext businessActionContext);

    /**
     * 二阶段回滚
     */
    boolean rollback(BusinessActionContext businessActionContext);
}
