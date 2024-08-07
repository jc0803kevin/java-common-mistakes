package icu.kevin.cache.avalanche;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RequestMapping("large")
@RestController
public class LargeKeyController {


    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("init")
    @Async
    public void initData(@RequestParam (name = "size", defaultValue = "5000") Integer size){
        log.info("starting init size........." + size);
        for (int i = 0; i < size; i++) {
            log.info("starting init data........." + i);
            redisTemplate.opsForSet().add("large_key_" + i, generateTestData(10 * 1024));
        }
    }


    @GetMapping("get")
    public String get(@RequestParam (name = "index", defaultValue = "0") Integer index){
        return redisTemplate.opsForSet().randomMember("large_key_" + index).toString();
    }


    public static String generateTestData(int sizeInKb) {
        int length = sizeInKb * 1024;
        int chunkSize = 10;
        int chunks = length / chunkSize;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < chunks; i++) {
            String chunk = generateRandomString(chunkSize);
            sb.append(chunk);
        }
        return sb.toString();
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

}
