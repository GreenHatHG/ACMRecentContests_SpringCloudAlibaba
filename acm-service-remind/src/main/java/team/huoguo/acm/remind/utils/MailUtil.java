package team.huoguo.acm.remind.utils;

import cn.hutool.json.JSONObject;

/**
 * @author GreenHatHG
 */
public class MailUtil {

    public static String remindMailContent(JSONObject jsonObject){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("你设置的提醒：\n");
        stringBuilder.append("比赛平台:");
        stringBuilder.append(jsonObject.getStr("oj"));
        stringBuilder.append("\n");
        stringBuilder.append("比赛名字:");
        stringBuilder.append(jsonObject.getStr("name"));
        stringBuilder.append("\n");
        stringBuilder.append("比赛开始时间:");
        stringBuilder.append(jsonObject.getStr("startTime"));
        stringBuilder.append("\n");
        stringBuilder.append("比赛结束时间:");
        stringBuilder.append(jsonObject.getStr("endTime"));
        stringBuilder.append("\n");
        stringBuilder.append("比赛链接:");
        stringBuilder.append(jsonObject.getStr("link"));
        stringBuilder.append("\n");
        stringBuilder.append("祝你比赛成功，AK全场!");
        return stringBuilder.toString();
    }

}
