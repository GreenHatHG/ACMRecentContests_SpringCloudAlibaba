package team.huoguo.acm.userinfo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import team.huoguo.acm.commons.utils.AbstractRedisUtil;

/**
 * @author GreenHatHG
 **/

@Component
public class RedisUtil extends AbstractRedisUtil {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
    }
}
