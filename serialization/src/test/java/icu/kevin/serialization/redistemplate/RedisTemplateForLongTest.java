package icu.kevin.serialization.redistemplate;

import icu.kevin.serialization.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Slf4j
public class RedisTemplateForLongTest extends BaseSpringTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void wrong2() {
        String key = "testCounter";
        redisTemplate.opsForValue().set(key, 1L);
        log.info("{} {}", redisTemplate.opsForValue().get(key), redisTemplate.opsForValue().get(key) instanceof Long);
        Long l1 = getLongFromRedis(key);

        // int 最大值
        redisTemplate.opsForValue().set(key, Integer.MAX_VALUE);
        log.info("{} {}", redisTemplate.opsForValue().get(key), redisTemplate.opsForValue().get(key) instanceof Integer);
        Long l2 = getLongFromRedis(key);

        //
        redisTemplate.opsForValue().set(key, Integer.MAX_VALUE + 1L);
        log.info("{} {}", redisTemplate.opsForValue().get(key), redisTemplate.opsForValue().get(key) instanceof Long);
        Long l3 = getLongFromRedis(key);
        log.info("{}, {}, {}", l1, l2, l3);
    }

    private Long getLongFromRedis(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        return null;
    }

}
