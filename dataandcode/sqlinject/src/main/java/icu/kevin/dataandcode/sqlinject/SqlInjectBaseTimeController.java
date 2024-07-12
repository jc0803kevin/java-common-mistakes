package icu.kevin.dataandcode.sqlinject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 时间盲注
 */

@Slf4j
@RequestMapping("time-based")
@RestController
public class SqlInjectBaseTimeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `users_time`;");
        jdbcTemplate.execute("create TABLE `users_time` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `username` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        jdbcTemplate.execute("INSERT INTO `users_time` (username,password) VALUES ('test1','haha1'),('test2','haha2'),('kevin','123456')");
    }

//    @ExceptionHandler
//    public void handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
//        log.warn(String.format("访问 %s -> %s 出现异常！", req.getRequestURI(), method.toString()), ex);
//    }

    @PostMapping("wrong")
    public List wrong(@RequestParam("username") String username) {
        //curl -X POST http://localhost:18081/time-based/wrong?username=test
        //python sqlmap.py -u  http://localhost:18081/time-based/wrong --data username=test --current-db --flush-session
        //python sqlmap.py -u  http://localhost:18081/time-based/wrong --data username=test --tables -D "sqlinject"
        //python sqlmap.py -u  http://localhost:18081/time-based/wrong --data username=test -D "sqlinject" -T "users_time" --dump

        List list = jdbcTemplate.queryForList("SELECT id,username FROM users_time WHERE username LIKE '%" + username + "%'");
        log.info("{}", list);

        return list;
    }


}
