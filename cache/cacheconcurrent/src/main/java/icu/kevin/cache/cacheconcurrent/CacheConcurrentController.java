package icu.kevin.cache.cacheconcurrent;


import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequestMapping("cacheconcurrent")
@RestController
public class CacheConcurrentController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger();
//    @Autowired
//    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        stringRedisTemplate.opsForValue().set("hotsopt", getExpensiveData(), 5, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @GetMapping("wrong")
    public String wrong() {
        // 一个热点key失效了，同时大量并发打过来，直接回源到数据库，造成数据库压力
        String data = stringRedisTemplate.opsForValue().get("hotsopt");
        if (StringUtils.isEmpty(data)) {
            data = getExpensiveData();
            stringRedisTemplate.opsForValue().set("hotsopt", data, 5, TimeUnit.SECONDS);
        }
        return data;
    }
//
//    @Autowired
//    private RedissonClient redissonClient;
//    @GetMapping("right")
//    public String right() {
//        String data = stringRedisTemplate.opsForValue().get("hotsopt");
//        if (StringUtils.isEmpty(data)) {
//            RLock locker = redissonClient.getLock("locker");
//            //获取分布式锁
//            if (locker.tryLock()) {
//                try {
//                    data = stringRedisTemplate.opsForValue().get("hotsopt");
//                    //双重检查，因为可能已经有一个B线程过了第一次判断，在等锁，然后A线程已经把数据写入了Redis中
//                    if (StringUtils.isEmpty(data)) {
//                        //回源到数据库查询
//                        data = getExpensiveData();
//                        stringRedisTemplate.opsForValue().set("hotsopt", data, 5, TimeUnit.SECONDS);
//                    }
//                } finally {
//                    //别忘记释放，另外注意写法，获取锁后整段代码try+finally，确保unlock万无一失
//                    locker.unlock();
//                }
//            }
//        }
//        return data;
//    }

    private String getExpensiveData() {
        atomicInteger.incrementAndGet();
        return "important data";
    }
}
