package team.huoguo.acm.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author GreenHatHG
 */

@SpringBootApplication(scanBasePackages = "team.huoguo.acm")
@EnableDiscoveryClient
@EnableBinding({Sink.class})
//开启异步功能,因为controller是同步并阻塞的
@EnableAsync
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
