package team.huoguo.acm.userinfo.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.UnauthorizedException;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.UserInfoService;
import team.huoguo.acm.userinfo.service.feign.MailService;
import team.huoguo.acm.userinfo.utils.Argon2Util;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @auther GreenHatHG
 */

@RestController
@ResponseBody
@Validated
public class RegisterController {

    @Autowired
    private UserInfoService service;

    @Resource
    private MailService mailService;

    @PostMapping("/actions/register")
    public Result register(@RequestParam @NotBlank @Size(max = 30) String username,
                           @RequestParam @NotBlank @Size(max = 30) String code,
                           @RequestParam @NotBlank @Size(max = 30) String password,
                           @RequestParam @NotBlank @Size(max = 30) @Email String mail) {
        service.registerUnique(username, mail);
        UserInfo userInfo = UserInfo.builder()
                .id(IdUtil.objectId())
                .createTime(System.currentTimeMillis())
                .username(username)
                .mail(mail)
                .password(Argon2Util.hash(password))
                .build();
        String message = mailService.verify(mail, code, "register");
        if(message.equals("ok")){
            service.insert(userInfo);
            return ResultFactory.buildSuccessResult("成功");
        }
        throw new UnauthorizedException(message);
    }

}
