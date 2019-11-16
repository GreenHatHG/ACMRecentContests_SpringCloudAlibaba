package team.huoguo.acm.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.CustomException;
import team.huoguo.acm.userinfo.utils.ImgCaptchaUtil;

/**
 * @author GreenHatHG
 **/

@RestController
@ResponseBody
public class CaptchaController {

    @Autowired
    private ImgCaptchaUtil imgCaptchaUtil;

    @GetMapping("/captcha")
    public Result getCaptcha()  {
        try{
            return ResultFactory.buildSuccessResult(imgCaptchaUtil.getCircleCaptcha());
        }catch (Exception e){
            throw new CustomException(450, "获取验证码失败");
        }
    }
}
