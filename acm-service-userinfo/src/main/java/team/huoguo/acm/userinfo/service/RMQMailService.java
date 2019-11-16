package team.huoguo.acm.userinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import team.huoguo.acm.commons.exception.CustomException;
import team.huoguo.acm.userinfo.utils.RedisUtil;

/**
 * @author GreenHatHG
 */

@Component
public class RMQMailService {

    @Autowired
    private MessageChannel output;

    @Autowired
    private RedisUtil redisUtil;

    @Async
    public void sendMail(String mail, String type){
        if(!redisUtil.canSend(mail+type)){
            throw new CustomException(470, "该邮箱请求过于频繁，请一分钟后再试");
        }
        output.send(MessageBuilder.withPayload(mail+ "," + type).build());
    }
}
