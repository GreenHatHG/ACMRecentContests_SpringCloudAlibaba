package team.huoguo.comsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.huoguo.comsumer.service.NacosProviderService;

import javax.annotation.Resource;

/**
 * @author GreenHatHG
 */

@RestController
public class NacosProdiverController {

    @Resource
    private NacosProviderService nacosProviderService;

    @GetMapping("/echo")
    public String echo(){
        return nacosProviderService.echo("xxx");
    }
}
