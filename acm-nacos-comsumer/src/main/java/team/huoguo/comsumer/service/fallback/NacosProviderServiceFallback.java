package team.huoguo.comsumer.service.fallback;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import team.huoguo.comsumer.service.NacosProviderService;

/**
 * @author GreenHatHG
 */

@Component
public class NacosProviderServiceFallback implements NacosProviderService {
    @Override
    public String echo(String msg) {
        return "fallback";
    }

}
