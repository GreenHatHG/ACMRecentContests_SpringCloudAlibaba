package team.huoguo.acm.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.acm.mail.service.VerificationService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author GreenHatHG
 */

@RestController
@Validated
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/code")
    public String verify(@RequestParam @NotBlank @Size(max = 30) String mail,
                          @RequestParam @NotBlank @Size(max = 30) String code,
                         @RequestParam @NotBlank @Size(max = 30) String type){
        return verificationService.verify(mail+type, code) ? "ok" : "验证码已过期";
    }
}
