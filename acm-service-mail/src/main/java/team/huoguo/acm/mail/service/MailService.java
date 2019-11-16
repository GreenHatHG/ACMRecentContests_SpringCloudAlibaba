package team.huoguo.acm.mail.service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.huoguo.acm.mail.utils.RedisUtil;

import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * 发邮件服务
 * @author GreenHatHG
 */

@Component
public class MailService {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisUtil redisUtil;

    @StreamListener("input")
    public void receive(String mail){
        Context context = new Context();
        String code = RandomUtil.randomNumbers(6);
        context.setVariable("registerCode", code);
        String emailTemplate = templateEngine.process("regCode", context);
        sendTemplateEmail("ACM赛事提醒与管理平台", emailTemplate, mail);
        redisUtil.setString(mail, "register"+code);
    }

    /**
     * 发送 HTML 模板邮件
     * @param subject
     * @param body
     * @param to
     */
    @Async
    public void sendTemplateEmail(String subject, String body, String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(applicationContext.getEnvironment().getProperty("spring.mail.username")));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {

        }
    }
}
