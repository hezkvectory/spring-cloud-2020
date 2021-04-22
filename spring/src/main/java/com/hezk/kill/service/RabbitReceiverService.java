package com.hezk.kill.service;

import com.hezk.kill.domain.ItemKillSuccess;
import com.hezk.kill.dto.KillDto;
import com.hezk.kill.dto.KillSuccessUserInfo;
import com.hezk.kill.dto.MailDto;
import com.hezk.kill.mapper.ItemKillSuccessMapper;
import com.hezk.kill.mapper.NewBeeMallOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class RabbitReceiverService {

    public static final Logger log = LoggerFactory.getLogger(RabbitReceiverService.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private IKillService killService;

    @Autowired
    private NewBeeMallOrderMapper orderMapper;


    /**
     * 秒杀异步邮件通知-接收消息
     */
//    @RabbitListener(queues = {"${mq.kill.item.success.email.queue}"})
    public void consumeEmailMsg(KillSuccessUserInfo info) {
        try {
            log.info("秒杀异步邮件通知-接收消息:{}", info);

            //TODO:真正的发送邮件....
            //简单文本
            //MailDto dto=new MailDto(env.getProperty("mail.kill.item.success.subject"),"这是测试内容",new String[]{info.getEmail()});
            //mailService.sendSimpleEmail(dto);

            //花哨文本
            final String content = String.format(env.getProperty("mail.kill.item.success.content"), info.getItemName(), info.getCode());
            MailDto dto = new MailDto(env.getProperty("mail.kill.item.success.subject"), content, new String[]{info.getEmail()});
            mailService.sendHTMLMail(dto);

        } catch (Exception e) {
            log.error("秒杀异步邮件通知-接收消息-发生异常：", e.fillInStackTrace());
        }
    }

    /**
     * 用户秒杀成功后超时未支付-监听者
     */
//    @RabbitListener(queues = {"${mq.kill.item.success.kill.dead.real.queue}"})
    public void consumeExpireOrder(KillSuccessUserInfo info) {
        try {
            log.info("用户秒杀成功后超时未支付-监听者-接收消息:{}", info);

            if (info != null) {
                ItemKillSuccess entity = itemKillSuccessMapper.selectByPrimaryKey(info.getCode());
                if (entity != null && entity.getStatus().intValue() == 0) {
                    itemKillSuccessMapper.expireOrder(info.getCode());

                    orderMapper.expireOrder(info.getCode());
                }
            }
        } catch (Exception e) {
            log.error("用户秒杀成功后超时未支付-监听者-发生异常：", e.fillInStackTrace());
        }
    }


    /**
     * 秒杀时异步接收Mq消息-监听者
     *
     * @param dto
     */
//    @RabbitListener(queues = {"${mq.kill.item.execute.limit.queue.name}"}, containerFactory = "multiListenerContainer")
    public void consumeKillExecuteMqMsg(KillDto dto) {
        try {
            if (dto != null) {
                //采用任何一种加分布锁的处理方法都是可行的
                killService.killItemV4(dto.getKillId(), dto.getUserId());
            }
        } catch (Exception e) {
            log.error("用户秒杀成功后超时未支付-监听者-发生异常：", e.fillInStackTrace());
        }
    }
}












