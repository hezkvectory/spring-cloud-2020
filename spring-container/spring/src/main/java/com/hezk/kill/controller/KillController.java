package com.hezk.kill.controller;

import com.google.common.collect.Maps;
import com.hezk.kill.dto.KillDto;
import com.hezk.kill.dto.KillSuccessUserInfo;
import com.hezk.kill.enums.StatusCode;
import com.hezk.kill.mapper.ItemKillSuccessMapper;
import com.hezk.kill.response.BaseResponse;
import com.hezk.kill.response.Result;
import com.hezk.kill.response.ResultBuilder;
import com.hezk.kill.service.IKillService;
import com.hezk.kill.service.RabbitSenderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;


@Controller
public class KillController {

    private static final String prefix = "kill";

    @Autowired
    private IKillService killService;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private RabbitSenderService rabbitSenderService;

    /***
     * 商品秒杀核心业务逻辑
     */
    @PostMapping(value = prefix + "/execute")
    @ResponseBody
    public Result execute(@RequestBody @Validated KillDto dto, BindingResult result, HttpServletRequest request) throws LoginException {
        String _userId = request.getHeader("userId");
        if (Objects.isNull(_userId)) {
            return ResultBuilder.validateFailure("用户没登录");
        }
        if (result.hasErrors() || dto.getKillId() <= 0) {
            return ResultBuilder.validateFailure("参数错误");
        }
        Integer userId = Integer.valueOf(_userId);

        try {
            Boolean res = killService.killItem(dto.getKillId(), userId);
            if (!res) {
                return ResultBuilder.systemError("哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
            return ResultBuilder.success();
        } catch (Exception e) {
            return ResultBuilder.systemError(e.getMessage());
        }
    }


    /***
     * 商品秒杀核心业务逻辑-用于压力测试
     */
    @PostMapping(value = prefix + "/execute/lock")
    @ResponseBody
    public Result executeLock(@RequestBody @Validated KillDto dto, BindingResult result) {
        if (result.hasErrors() || dto.getKillId() <= 0) {
            return ResultBuilder.validateFailure("参数不合法");
        }
        try {
            //不加分布式锁的前提
            Boolean res = killService.killItemV2(dto.getKillId(), dto.getUserId());

            //基于Redis的分布式锁进行控制
            /*Boolean res=killService.killItemV3(dto.getKillId(),dto.getUserId());
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"基于Redis的分布式锁进行控制-哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }*/

            //基于Redisson的分布式锁进行控制
            /*Boolean res=killService.killItemV4(dto.getKillId(),dto.getUserId());
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"基于Redisson的分布式锁进行控制-哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }*/

            //基于ZooKeeper的分布式锁进行控制
//            Boolean res = killService.killItemV5(dto.getKillId(), dto.getUserId());
            if (!res) {
                return ResultBuilder.systemError("商品已抢购完毕或者不在抢购时间段哦!");
            }
            return ResultBuilder.success();
        } catch (Exception e) {
            return ResultBuilder.rejectionError(e.getMessage());
        }
    }


    //http://localhost:8083/kill/kill/record/detail/343147116421722112

    /**
     * 查看订单详情
     */
    @GetMapping(value = prefix + "/record/detail/{orderNo}")
    public String killRecordDetail(@PathVariable String orderNo, ModelMap modelMap) {
        if (StringUtils.isBlank(orderNo)) {
            return "error";
        }
        KillSuccessUserInfo info = itemKillSuccessMapper.selectByCode(orderNo);
        if (info == null) {
            return "error";
        }
        modelMap.put("info", info);
        return "killRecord";
    }

    /**
     * 商品秒杀核心业务逻辑-mq限流
     */
    @PostMapping(value = prefix + "/execute/mq")
    public BaseResponse executeMq(@RequestBody @Validated KillDto dto, BindingResult result, HttpServletRequest request) {
        String _userId = request.getHeader("userId");
        if (Objects.isNull(_userId)) {
            return new BaseResponse(StatusCode.UserNotLogin);
        }
        if (result.hasErrors() || dto.getKillId() <= 0) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        Integer userId = Integer.valueOf(_userId);

        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> dataMap = Maps.newHashMap();
        try {
            dataMap.put("killId", dto.getKillId());
            dataMap.put("userId", userId);
            response.setData(dataMap);

            dto.setUserId(userId);
            rabbitSenderService.sendKillExecuteMqMsg(dto);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    /**
     * 商品秒杀核心业务逻辑-mq限流-立马跳转至抢购结果页
     */
    @GetMapping(value = prefix + "/execute/mq/to/result")
    public String executeToResult(@RequestParam Integer killId, HttpSession session, ModelMap modelMap) {
        Object uId = session.getAttribute("uid");
        if (uId != null) {
            Integer userId = (Integer) uId;

            modelMap.put("killId", killId);
            modelMap.put("userId", userId);
        }
        return "executeMqResult";
    }

    /**
     * 商品秒杀核心业务逻辑-mq限流-在抢购结果页中发起抢购结果的查询
     */
    @GetMapping(value = prefix + "/execute/mq/result")
    @ResponseBody
    public BaseResponse executeResult(@RequestParam Integer killId, @RequestParam Integer userId) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            Map<String, Object> resMap = killService.checkUserKillResult(killId, userId);
            response.setData(resMap);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    /**
     * 商品秒杀核心业务逻辑-mq限流-JMeter压测
     */
    @PostMapping(value = prefix + "/execute/mq/lock")
    public BaseResponse executeMqLock(@RequestBody @Validated KillDto dto, BindingResult result) {
        if (result.hasErrors() || dto.getKillId() <= 0) {
            return new BaseResponse(StatusCode.InvalidParams);
        }

        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> dataMap = Maps.newHashMap();
        try {
            dataMap.put("killId", dto.getKillId());
            dataMap.put("userId", dto.getUserId());
            response.setData(dataMap);

            rabbitSenderService.sendKillExecuteMqMsg(dto);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }
}








































