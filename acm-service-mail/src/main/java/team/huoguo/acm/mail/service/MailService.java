package team.huoguo.acm.mail.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.SimpleMailMessage;
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
    public void receive(String content){
        JSONObject jsonObject = JSONUtil.parseObj(content);
        if(!"remind".equals(jsonObject.get("type"))){
            Context context = new Context();
            String code = RandomUtil.randomNumbers(6);
            context.setVariable("code", code);
            String emailTemplate = templateEngine.process("code", context);
            sendTemplateEmail("ACM赛事提醒与管理平台", emailTemplate, jsonObject.getStr("to"));
            redisUtil.setString(jsonObject.getStr("to")+jsonObject.getStr("type"), code);
        }else{
            sendSimpleMail(jsonObject.getStr("to"), jsonObject.getStr("content"));
        }
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
            e.printStackTrace();
        }
    }

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param content 内容
     */
    public void sendSimpleMail(String to, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom("acmrecentcontents@aliyun.com");
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject("ACM赛事提醒与管理平台");
        //邮件内容
        message.setText(content);
        //发送邮件
        javaMailSender.send(message);
    }
}
