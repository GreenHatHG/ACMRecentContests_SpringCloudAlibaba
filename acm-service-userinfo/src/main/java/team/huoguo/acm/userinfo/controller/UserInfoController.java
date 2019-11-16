package team.huoguo.acm.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.userinfo.domain.UserInfo;
import team.huoguo.acm.userinfo.service.UserInfoService;

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
    public UserInfo getOne(String queryKey, String queryValue){
        return userInfoService.findOne(queryKey, queryValue);
    }

    @GetMapping("/userinfo/password")
    public String getPassword(String queryKey, String queryValue){
        return userInfoService.findOne(queryKey, queryValue).getPassword();
    }

}
