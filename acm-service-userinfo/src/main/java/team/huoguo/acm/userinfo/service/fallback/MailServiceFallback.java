package team.huoguo.acm.userinfo.service.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import team.huoguo.acm.userinfo.service.feign.MailService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@Component
public class MailServiceFallback implements MailService {

    @Override
    public String verify(@RequestParam @NotBlank @Size(max = 30) String mail,
                         @RequestParam @NotBlank @Size(max = 30) String code,
                         @RequestParam @NotBlank @Size(max = 30) String type){
        return "请求失败，请检查你的网络！";
    }
}
