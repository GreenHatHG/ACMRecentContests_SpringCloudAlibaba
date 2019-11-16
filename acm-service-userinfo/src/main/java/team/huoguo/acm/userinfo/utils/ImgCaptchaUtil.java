package team.huoguo.acm.userinfo.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

;

/**
 * 图像验证码工具
 * @author GreenHatHG
 **/

@Component
public class ImgCaptchaUtil {

    private RedisUtil redisUtil;
    /**
     * 定义图形验证码的长、宽
     */
    private LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public Map<String, String> getCircleCaptcha(){
        //重新生成验证码
        captcha.createCode();
        //生成不带-的UUID字符串不再需要做字符替换，性能提升一倍左右
        Map<String, String> imgCaptcha = new HashMap<>(2);
        imgCaptcha.put("id", IdUtil.simpleUUID());
        imgCaptcha.put("base64", captcha.getImageBase64());
        redisUtil.setString(imgCaptcha.get("id"), captcha.getCode());
        return imgCaptcha;
    }
}
