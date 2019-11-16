package team.huoguo.acm.gateway.service.fallback;

import org.springframework.stereotype.Component;
import team.huoguo.acm.gateway.service.feign.UserInfoService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@Component
public class UserInfoServiceFallback implements UserInfoService {


    @Override
    public String getPassword(@NotBlank @Size(max = 30) String queryValue) {
        return "";
    }
}
