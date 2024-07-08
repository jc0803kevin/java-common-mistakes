package icu.kevin.serialization.enumusedinapi;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("enumusedinapi")
@RestController
public class EnumUsedInAPIController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Cannot deserialize value of type `icu.kevin.serialization.enumusedinapi.StatusEnumClient` from number 5: index value outside legal index range [0..4]
     *
     * StatusEnumServer 有CANCELED这个枚举值
     * StatusEnumClient 没有CANCELED这个枚举值
     *
     * 反序列化的时候，该索引下标枚举值不存在，导致异常
     *
     */
    @GetMapping("getOrderStatusClient")
    public void getOrderStatusClient() {
        StatusEnumClient result = restTemplate.getForObject("http://localhost:45678/enumusedinapi/getOrderStatus", StatusEnumClient.class);
        log.info("result {}", result);
    }

    /**
     * 请求接口可以看到，传入的是 CREATED 和 PAID，返回的居然是 DELIVERED 和 FINISHED
     *
     * 序列化走了 status 的值，而反序列化并没有根据 status 来，还是使用了枚举的 ordinal() 索引值。
     * https://github.com/FasterXML/jackson-databind/issues/1850
     */
    @GetMapping("queryOrdersByStatusListClient")
    public void queryOrdersByStatusListClient() {
        List<StatusEnumClient> request = Arrays.asList(StatusEnumClient.CREATED, StatusEnumClient.PAID);
        HttpEntity<List<StatusEnumClient>> entity = new HttpEntity<>(request, new HttpHeaders());
        List<StatusEnumClient> response = restTemplate.exchange("http://localhost:45678/enumusedinapi/queryOrdersByStatusList",
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<StatusEnumClient>>() {
                }).getBody();
        log.info("result {}", response);
    }

    @GetMapping("getOrderStatus")
    public StatusEnumServer getOrderStatus() {
        return StatusEnumServer.CANCELED;
    }

    @PostMapping("queryOrdersByStatusList")
    public List<StatusEnumServer> queryOrdersByStatus(@RequestBody List<StatusEnumServer> enumServers) {
        enumServers.add(StatusEnumServer.CANCELED);
        return enumServers;
    }
}
