package icu.kevin.serialization.redistemplate;

import icu.kevin.serialization.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@Slf4j
public class RedisTemplateTest2 extends BaseSpringTest {

    // https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, String> stringRedisTemplate2;


    // redisTemplate<K,V> 注入失败参考 https://blog.csdn.net/zhaoheng314/article/details/81564166
    // @Resource 是根据名称注入，在springBoot框架中是装配了“redisTemplate”这个bean的
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void right2() {
        User user = new User("kevin", 36);
        redisTemplate.opsForValue().set(user.getName(), user);
        log.info("userRedisTemplate get {}", redisTemplate.opsForValue().get(user.getName()).getClass());

        User userFromRedis = (User) redisTemplate.opsForValue().get(user.getName());

        log.info("userRedisTemplate get {} {}", userFromRedis, userFromRedis.getClass());
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get(user.getName()));
    }

    @Test
    public void compare(){

        log.info("StringRedisTemplate 的hashcode：{}", stringRedisTemplate.hashCode());
        log.info("RedisTemplate<String, String> 的hashcode：{}", stringRedisTemplate2.hashCode());

        // true 说明两个是同一个对象 StringRedisTemplate == RedisTemplate<String, String>
        log.info("equal()的结果：{}", stringRedisTemplate.equals(stringRedisTemplate2));

    }


}
