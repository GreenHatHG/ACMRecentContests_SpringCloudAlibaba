package team.huoguo.acm.remind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author GreenHatHG
 */

@SpringBootApplication(scanBasePackages = "team.huoguo.acm")
@EnableDiscoveryClient
@EnableBinding({Source.class})
//开启异步功能,因为controller是同步并阻塞的
@EnableAsync
@EnableScheduling
public class RemindApplication {
    public static void main(String[] args) {
        SpringApplication.run(RemindApplication.class, args);
    }
}
