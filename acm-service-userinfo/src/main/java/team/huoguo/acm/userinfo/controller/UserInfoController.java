package team.huoguo.acm.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.UserInfoService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@RestController
@ResponseBody
@Validated
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/userinfo")
    public UserInfo getOne(@RequestParam @NotBlank @Size(max = 30) String queryKey,
                           @RequestParam @NotBlank @Size(max = 30) String queryValue){
        return userInfoService.findOne(queryKey, queryValue);
    }

    @GetMapping("/userinfo/password")
    public String getPassword(@RequestParam @NotBlank @Size(max = 30) String queryValue){
        return userInfoService.findOne("password", queryValue).getPassword();
    }

}
