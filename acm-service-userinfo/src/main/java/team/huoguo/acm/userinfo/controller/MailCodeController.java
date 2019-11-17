package team.huoguo.acm.userinfo.controller;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.CustomException;
import team.huoguo.acm.commons.exception.NotFoundException;
import team.huoguo.acm.commons.exception.UnauthorizedException;
import team.huoguo.acm.commons.utils.JWTUtil;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.RMQMailService;
import team.huoguo.acm.userinfo.service.UserInfoService;
import team.huoguo.acm.userinfo.utils.RedisUtil;

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
    private RedisUtil redisUtil;

    @GetMapping("/register_code")
    public Result getRegisterCode(@RequestParam @NotBlank @Size(max = 30) String username,
                          @RequestParam @NotBlank @Size(max = 30) @Email String mail){
        userInfoService.registerUnique(username, mail);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", mail);
        jsonObject.put("type", "register");
        check(jsonObject.toString());
        rmqMailService.sendMail(jsonObject.toString());
        return ResultFactory.buildSuccessResult("发送成功");
    }

    @GetMapping("/resetpwd_code")
    public Result getResetPwdCode(@RequestParam @NotBlank @Size(max = 30) @Email String mail){
        if(userInfoService.findOne("mail", mail) == null){
            throw new NotFoundException("该邮箱尚未注册");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", mail);
        jsonObject.put("type", "resetpwd");
        check(jsonObject.toString());
        rmqMailService.sendMail(jsonObject.toString());
        return ResultFactory.buildSuccessResult("发送成功");
    }

    @GetMapping("/resetmail_code")
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", originalEmail);
        jsonObject.put("type", "resetmail");
        check(jsonObject.toString());
        rmqMailService.sendMail(jsonObject.toString());
        return ResultFactory.buildSuccessResult("发送成功");
    }

    private void check(String content){
        if(!redisUtil.canSend(content)){
            throw new CustomException(470, "该邮箱请求过于频繁，请一分钟后再试");
        }
    }
}
