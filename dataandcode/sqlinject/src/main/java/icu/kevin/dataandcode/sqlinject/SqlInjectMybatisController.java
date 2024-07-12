package icu.kevin.dataandcode.sqlinject;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping("sqlinject")
@Slf4j
@RestController
public class SqlInjectMybatisController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDataMapper userDataMapper;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `userdata`;");
        jdbcTemplate.execute("create TABLE `userdata` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        jdbcTemplate.execute("INSERT INTO `userdata` (name,password) VALUES ('test1','haha1'),('test2','haha2'),('kevin','123456')");
    }

    @PostMapping("mybatiswrong")
    public List mybatiswrong(@RequestParam("name") String name) {
        //curl -X POST http://localhost:18081/sqlinject/mybatiswrong?name=test
        //python sqlmap.py -u  http://localhost:18081/sqlinject/mybatiswrong --data name=test --current-db --flush-session
        return userDataMapper.findByNameWrong(name);
    }

    @PostMapping("mybatisright")
    public List mybatisright(@RequestParam("name") String name) {
        //curl -X POST http://localhost:18081/sqlinject/mybatisright?name=test
        // python sqlmap.py -u  http://localhost:18081/sqlinject/mybatisright --data name=test --current-db --flush-session
        return userDataMapper.findByNameRight(name);
    }

    @PostMapping("mybatiswrong2")
    public List mybatiswrong2(@RequestParam("names") String names) {
        //curl -X POST http://localhost:18081/sqlinject/mybatiswrong2?names='test1','test2'
        //python sqlmap.py -u  http://localhost:18081/sqlinject/mybatiswrong2 --data names="'test1','test2'"
        return userDataMapper.findByNamesWrong(names);
    }

    @PostMapping("mybatisright2")
    public List mybatisright2(@RequestParam("names") List<String> names) {
        //curl -X POST http://localhost:18081/sqlinject/mybatisright2?names=test1,test2
        return userDataMapper.findByNamesRight(names);
    }

    @PostMapping("dynamic")
    public List findByNameDynamic(@RequestParam("name") String name) {
        //curl -X POST http://localhost:18081/sqlinject/dynamic?name=test
        // python sqlmap.py -u  http://localhost:18081/sqlinject/dynamic --data name=test --current-db --flush-session
        return userDataMapper.findByNameDynamic(name);
    }
}
