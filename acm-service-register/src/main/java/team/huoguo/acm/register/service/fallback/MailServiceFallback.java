package team.huoguo.acm.register.service.fallback;

import org.springframework.stereotype.Component;
import team.huoguo.acm.register.service.feign.MailService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@Component
public class MailServiceFallback implements MailService {

    @Override
    public String verify(@NotBlank @Size(max = 30) String mail, @NotBlank @Size(max = 30) String code) {
        return "请求失败，请检查你的网络！";
    }
}
