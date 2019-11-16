package team.huoguo.acm.userinfo.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.huoguo.acm.userinfo.service.fallback.MailServiceFallback;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@FeignClient(value = "mail", fallback = MailServiceFallback.class)
public interface MailService {

    @PostMapping("/code")
    public String verify(@RequestParam @NotBlank @Size(max = 30) String mail,
                         @RequestParam @NotBlank @Size(max = 30) String code,
                         @RequestParam @NotBlank @Size(max = 30) String type);
}
