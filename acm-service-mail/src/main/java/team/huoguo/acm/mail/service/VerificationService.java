package team.huoguo.acm.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.huoguo.acm.mail.utils.RedisUtil;

/**
 * 验证注册时提供的验证码是否正确
 * @author GreenHatHG
 */

@Component
public class VerificationService {

    @Autowired
    private RedisUtil redisUtil;

    public Boolean verify(String mail, String code){
        if(("register"+code).equals(redisUtil.getString(mail))){
            redisUtil.deleteKey(mail);
            return true;
        }
        return false;
    }
}
