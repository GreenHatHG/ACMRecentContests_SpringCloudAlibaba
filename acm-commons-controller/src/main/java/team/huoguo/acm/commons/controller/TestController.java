package team.huoguo.acm.commons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GreenHatHG
 */

@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "test";
    }
}
