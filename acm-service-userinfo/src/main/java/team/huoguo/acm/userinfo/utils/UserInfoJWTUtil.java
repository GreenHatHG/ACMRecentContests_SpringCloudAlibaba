package team.huoguo.acm.userinfo.utils;

import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.utils.JWTUtil;
import team.huoguo.acm.userinfo.domain.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GreenHatHG
 */
public class UserInfoJWTUtil extends JWTUtil {

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. userInfo
     *
     * @param userInfo
     * @return
     */
    public static Result generateUserInfo(UserInfo userInfo) {
        Map<String, Object> responseBean = new HashMap<>(2);
        String token = sign(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword());
        responseBean.put("token", token);
        userInfo.setPassword("");
        responseBean.put("userInfo", userInfo);
        return ResultFactory.buildSuccessResult(responseBean);
    }
}
