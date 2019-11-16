package team.huoguo.acm.gateway.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.huoguo.acm.gateway.service.fallback.UserInfoServiceFallback;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */
@FeignClient(value = "acm-service-userinfo", fallback = UserInfoServiceFallback.class)
public interface UserInfoService {

    @GetMapping("/userinfo/password")
    public String getPassword(@RequestParam @NotBlank @Size(max = 30) String queryValue);
}
