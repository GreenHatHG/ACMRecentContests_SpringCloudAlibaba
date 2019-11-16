package team.huoguo.acm.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.CustomException;
import team.huoguo.acm.commons.exception.UnauthorizedException;
import team.huoguo.acm.commons.exception.UniqueException;
import team.huoguo.acm.commons.utils.JWTUtil;
import team.huoguo.acm.userinfo.service.UserInfoService;
import team.huoguo.acm.userinfo.service.feign.MailService;
import team.huoguo.acm.userinfo.utils.FileHandleUtil;
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
public class UpdateController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserInfoService userInfoService;

    @PutMapping("/actions/resetpwd")
    public Result reset(@RequestParam @NotBlank @Size(max = 30) String mail,
                        @RequestParam @NotBlank @Size(max = 30) String code,
                        @RequestParam @NotBlank @Size(max = 30) String password){
        String message = mailService.verify(mail, code, "resetpwd");
        if(message.equals("ok")){
            userInfoService.updateOne("mail", mail, "password", password);
            return ResultFactory.buildSuccessResult("成功");
        }
        throw new UnauthorizedException(message);
    }

    @PutMapping("/username")
    public Result updateUserName(HttpServletRequest request,
                                 @NotBlank @Size(max = 30) String username){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        if(!userInfoService.exists("id", id)){
            throw new UnauthorizedException("用户校验失败,请重新登录");
        }
        userInfoService.updateOne("id", id, "username", username);
        return ResultFactory.buildSuccessResult("成功");
    }

    @PutMapping("/city")
    public Result updateCity(HttpServletRequest request,
                             @NotBlank @Size(max = 30) String city){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        if(!userInfoService.exists("id", id)){
            throw new UnauthorizedException("用户校验失败,请重新登录");
        }
        userInfoService.updateOne("id", id, "city", city);
        return ResultFactory.buildSuccessResult("成功");
    }

    @PutMapping("mail")
    public Result updateEmail(@NotBlank @Size(max = 30) String originalEmail,
                              @NotBlank @Size(max = 30)  String code,
                              @NotBlank @Size(max = 30) @Email String newEmail){
        String message = mailService.verify(originalEmail, code, "resetmail");
        if(message.equals("ok")){
            if(userInfoService.exists("mail", newEmail)){
                throw new UniqueException("该邮箱已存在");
            }
            userInfoService.updateOne("mail", originalEmail, "mail", newEmail);
            return ResultFactory.buildSuccessResult("成功");
        }else{
            throw new UnauthorizedException(message);
        }
    }

    @PutMapping("avatar")
    public Result updateAvatar(HttpServletRequest request,
                               @NotBlank @Size(max = 100) String avatarUrl){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        if(!userInfoService.exists("id", id)){
            throw new UnauthorizedException("用户校验失败,请重新登录");
        }
        userInfoService.updateOne("id", id, "avatar", avatarUrl);
        try{
            FileHandleUtil.saveAvatarFromUrl(avatarUrl, id);
        }catch (Exception e){
            throw new CustomException(480, "更新失败, 请重试");
        }
        return ResultFactory.buildSuccessResult("成功");
    }
}
