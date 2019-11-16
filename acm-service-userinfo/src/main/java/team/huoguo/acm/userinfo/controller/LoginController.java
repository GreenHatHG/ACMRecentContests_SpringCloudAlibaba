package team.huoguo.acm.userinfo.controller;

import cn.hutool.core.lang.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.exception.UnauthorizedException;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.UserInfoService;
import team.huoguo.acm.userinfo.utils.Argon2Util;
import team.huoguo.acm.userinfo.utils.RedisUtil;
import team.huoguo.acm.userinfo.utils.UserInfoJWTUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@RestController
@ResponseBody
@Validated
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/actions/login")
    public Result login(@RequestParam @NotBlank @Size(max = 30) String code,
                        @RequestParam @NotBlank @Size(max = 50) String imgId,
                        @RequestParam @NotBlank @Size(max = 30) String username,
                        @RequestParam @NotBlank @Size(max = 30) String password) {
        if(!code.toLowerCase().equals(redisUtil.getString(imgId))){
            throw new UnauthorizedException("验证码错误");
        }
        redisUtil.deleteKey(imgId);

        UserInfo userInfo = null;
        if(!Validator.isEmail(username)){
            userInfo = userInfoService.findOne("username", username);
        }else{
            userInfo = userInfoService.findOne("mail", username);
        }

        if (userInfo == null || !Argon2Util.verify(userInfo.getPassword(), password)) {
            throw new UnauthorizedException("用户名或密码错误");
        }
        return UserInfoJWTUtil.generateUserInfo(userInfo);
    }

}
