package team.huoguo.acm.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.register.service.RMQMailService;
import team.huoguo.acm.register.service.UserInfoService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@RestController
@ResponseBody
public class CodeController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private RMQMailService rmqMailService;

    @PostMapping("/code")
    public Result getCode(@RequestParam @NotBlank @Size(max = 30) String username,
                          @RequestParam @NotBlank @Size(max = 30) @Email String mail){
        service.registerUnique(username, mail);
        rmqMailService.sendMail(mail);
        return ResultFactory.buildSuccessResult("发送成功");
    }
}
