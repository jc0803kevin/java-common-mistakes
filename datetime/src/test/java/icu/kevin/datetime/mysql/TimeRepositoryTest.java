package icu.kevin.datetime.mysql;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Slf4j
public class TimeRepositoryTest extends BaseSpringTest{

    @Autowired
    private TimeRepository timeRepository;

    @Test
    public void saveAndFindById(){
        Long id = System.currentTimeMillis();

        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setId(id);
        // 数据库值：2024-07-09 08:59:04
        // 程序取出来：2024-07-09T16:59:04（执行时的北京时间）
        timeEntity.setMydatetime1(LocalDateTime.now());
        // 数据库值：2024-07-09 08:59:04
        // 程序取出来：2024-07-09T16:59:04（执行时的北京时间）
        timeEntity.setMytimestamp1(LocalDateTime.now());
        // 数据库值：2024-07-09 20:59:04
        // 程序取出来：2024-07-10T04:59:04+08:00[Asia/Shanghai] 取数据的时候会根据serverTimezone转换
        timeEntity.setMydatetime2(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));
        // 数据库值：2024-07-09 20:59:04
        // 程序取出来：2024-07-10T04:59:04+08:00[Asia/Shanghai] 取数据的时候会根据serverTimezone转换
        timeEntity.setMytimestamp2(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));
        // 2024-07-09 16:59:04 字符串落入时啥样，取出来就是啥样
        timeEntity.setMydatetime3(LocalDateTime.now().toString());
        // 2024-07-09 16:59:04
        timeEntity.setMytimestamp3(LocalDateTime.now().toString());
        timeRepository.save(timeEntity);

        log.info("{}", timeRepository.findById(id).get());
    }

    @Test
    public void findById(){

        log.info("{}", timeRepository.findById(1720515543654L).get());
    }

}
