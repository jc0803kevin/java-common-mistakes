package icu.kevin.cache.avalanche;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
//        for (int i = 0; i < size; i++) {
//            log.info("starting init data........." + i);
////            redisTemplate.opsForValue().set("large_key_" + i, generateTestData(10 * 1024));
//
//        }

        redisTemplate.opsForValue().set("string_large_key1", generateTestData(10* 1024));
        redisTemplate.opsForValue().set("string_large_key2", generateTestData(10* 1024));

        redisTemplate.opsForSet().add("set_large_key1", new HashSet<>(50000));
        redisTemplate.opsForSet().add("set_large_key2", new HashSet<>(50000));

        redisTemplate.opsForHash().putAll("hash_large_key1", buildMapData(50000));
        redisTemplate.opsForHash().putAll("hash_large_key2", buildMapData(50000));

        redisTemplate.opsForList().rightPushAll("list_large_key1", buildListData(50000));
        redisTemplate.opsForList().rightPushAll("list_large_key2", buildListData(50000));
    }



    private Map buildMapData(int initialCapacity){

        Map<String, String> result =new HashMap<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            result.put("kevin_" + i, "123");
        }
        return result;

    }

    private List<String> buildListData(int initialCapacity){
        List<String> result = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            result.add("kevin_" + i );
        }
        return result;

    }




    @GetMapping("get")
    public String get(@RequestParam (name = "index", defaultValue = "0") Integer index){
//        return redisTemplate.opsForSet().randomMember("large_key_" + index).toString();
        return redisTemplate.opsForValue().get("large_key_" + index).toString();
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
