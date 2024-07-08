package icu.kevin.serialization.deserializationconstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.kevin.serialization.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DeserializationConstructorTest extends BaseSpringTest {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * jackson 默认使用无参构造方法，当没有其他委托指定的@JsonCreator方法就会抛出异常
     *
     *  result :APIResultWrong(success=false, code=1234)
     *  result :APIResultWrong(success=false, code=2000)
     * @throws JsonProcessingException
     */
    @Test
    public void wrong() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultWrong.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultWrong.class));
    }

    /**
     * result :APIResultRight(success=false, code=1234)
     * result :APIResultRight(success=true, code=2000)
     * @throws JsonProcessingException
     */
    @Test
    public void right() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultRight.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultRight.class));
    }
}
