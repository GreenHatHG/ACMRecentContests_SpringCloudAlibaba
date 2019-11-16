package team.huoguo.acm.mail.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author GreenHatHG
 **/

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 过期时间，三分钟
     */
    private final static int EXPIRE_TIME = 3*60;
    /**
     * 指定缓存失效时间
     * @param key  key值
     * @param time 缓存时间
     */
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 普通缓存放入String类型的value
     *
     * @param key   键值
     * @param value 值
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        //三分钟过期
        expire(key, EXPIRE_TIME);
    }

    /**
     *  key中储存的数字值加value
     *  如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * @param key
     * @param value
     */
    public void incr(String key, long value){
        redisTemplate.opsForValue().increment(key, value);
        expire(key, EXPIRE_TIME);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        return key == null ? "" : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除对应的key
     * @param key
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 是否在一分钟之内发送过邮件
     * @param key
     * @return
     */
    public boolean canSend(String key){
        if(redisTemplate.hasKey(key)){
            return false;
        }else{
            redisTemplate.opsForValue().set(key, "");
            expire(key, 60);
            return true;
        }
    }
}
