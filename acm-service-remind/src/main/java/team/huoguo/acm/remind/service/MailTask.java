package team.huoguo.acm.remind.service;

import cn.hutool.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author GreenHatHG
 **/

@Component
public class MailTask extends QuartzJobBean{

    @Autowired
    private RMQMailService rmqMailService;

    @Override
    public void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", jobDataMap.getString("to"));
        jsonObject.put("content", jobDataMap.getString("content"));
        jsonObject.put("type", "remind");
        rmqMailService.sendMail(jsonObject.toString());
    }
}
