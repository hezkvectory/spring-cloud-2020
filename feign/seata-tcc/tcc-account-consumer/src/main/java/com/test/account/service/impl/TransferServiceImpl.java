package com.test.account.service.impl;

import com.test.account.service.FirstAccountService;
import com.test.account.service.SecondAccountService;
import com.test.account.service.TransferService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 转账服务实现
 */
@Service
public class TransferServiceImpl implements TransferService {

    @DubboReference
    private FirstAccountService firstAccountService;

    @DubboReference
    private SecondAccountService secondAccountService;

    /**
     * 转账操作
     */
    @Override
    @GlobalTransactional
    public boolean transfer(final String from, final String to, final int amount) {
        //扣钱参与者，一阶段执行
        boolean ret = firstAccountService.prepareMinus(null, from, amount);

        if (!ret) {
            //扣钱参与者，一阶段失败; 回滚本地事务和分布式事务
            throw new RuntimeException("账号:[" + from + "] 预扣款失败");
        }

        //加钱参与者，一阶段执行
        ret = secondAccountService.prepareAdd(null, to, amount);

        if (!ret) {
            throw new RuntimeException("账号:[" + to + "] 预收款失败");
        }

        System.out.println(String.format("transfer amount[%s] from [%s] to [%s] finish.", String.valueOf(amount), from, to));
        return true;
    }

}
