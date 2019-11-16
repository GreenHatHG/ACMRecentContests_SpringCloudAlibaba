package team.huoguo.acm.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.NotFoundException;
import team.huoguo.acm.commons.exception.UnauthorizedException;
import team.huoguo.acm.commons.utils.JWTUtil;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.RMQMailService;
import team.huoguo.acm.userinfo.service.UserInfoService;
import team.huoguo.acm.userinfo.service.feign.MailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@RestController
@ResponseBody
@Validated
public class MailCodeController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RMQMailService rmqMailService;

    @Autowired
    private MailService mailService;

    @GetMapping("/register_code")
    public Result getRegisterCode(@RequestParam @NotBlank @Size(max = 30) String username,
                          @RequestParam @NotBlank @Size(max = 30) @Email String mail){
        userInfoService.registerUnique(username, mail);
        rmqMailService.sendMail(mail, "register");
        return ResultFactory.buildSuccessResult("发送成功");
    }

    @GetMapping("/resetpwd_code")
    public Result getResetPwdCode(@RequestParam @NotBlank @Size(max = 30) @Email String mail){
        if(userInfoService.findOne("mail", mail) == null){
            throw new NotFoundException("用户校验失败,请重新登录");
        }
        rmqMailService.sendMail(mail, "resetpwd");
        return ResultFactory.buildSuccessResult("发送成功");
    }

    @GetMapping("resetmail_code")
    public Result getEmailVerificationCode(HttpServletRequest request,
                                           @NotBlank @Size(max = 30) @Email String originalEmail){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        if(!userInfoService.exists("id", id)){
            throw new UnauthorizedException("用户校验失败,请重新登录");
        }
        UserInfo userInfo = userInfoService.findOne("mail", originalEmail);
        if(!originalEmail.equals(userInfo.getMail())){
            throw new UnauthorizedException("输入邮箱和原邮箱不一致");
        }
        rmqMailService.sendMail(originalEmail, "resetmail");
        return ResultFactory.buildSuccessResult("发送成功");
    }


}
