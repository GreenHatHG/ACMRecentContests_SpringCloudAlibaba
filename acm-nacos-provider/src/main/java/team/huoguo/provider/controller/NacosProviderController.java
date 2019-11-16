package team.huoguo.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GreenHatHG
 */

@RestController
public class NacosProviderController {

    @GetMapping("/echo/{msg}")
    public String echo(@PathVariable(value = "msg") String msg){
        return msg;
    }
}
