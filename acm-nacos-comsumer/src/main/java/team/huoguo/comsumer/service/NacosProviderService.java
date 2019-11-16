package team.huoguo.comsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.huoguo.comsumer.service.fallback.NacosProviderServiceFallback;

/**
 * @author GreenHatHG
 */

@FeignClient(value = "nacos-provider", fallback = NacosProviderServiceFallback.class)
public interface NacosProviderService {

    @GetMapping("/echo/{msg}")
    String echo(@PathVariable(value = "msg") String msg);
}
