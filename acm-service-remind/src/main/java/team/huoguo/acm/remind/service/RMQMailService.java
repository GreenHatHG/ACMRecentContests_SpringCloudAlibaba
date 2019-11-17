package team.huoguo.acm.remind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author GreenHatHG
 */

@Component
public class RMQMailService {

    @Autowired
    private MessageChannel output;

    @Async
    public void sendMail(String content){
        output.send(MessageBuilder.withPayload(content).build());
    }
}
