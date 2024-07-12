package icu.kevin.dataandcode.sqlinject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基于错误的注入
 */

@Slf4j
@RequestMapping("error-based")
@RestController
public class SqlInjectBaseErrorController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `users`;");
        jdbcTemplate.execute("create TABLE `users` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `username` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        jdbcTemplate.execute("INSERT INTO `users` (username,password) VALUES ('test1','haha1'),('test2','haha2'),('kevin','123456')");
    }

    @ExceptionHandler
    public void handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        log.warn(String.format("访问 %s -> %s 出现异常！", req.getRequestURI(), method.toString()), ex);
    }

    @PostMapping("wrong")
    public void wrong(@RequestParam("username") String username) {
        //curl -X POST http://localhost:18081/error-based/wrong?username=test
        //python sqlmap.py -u  http://localhost:18081/error-based/wrong --data username=test --current-db --flush-session
        //python sqlmap.py -u  http://localhost:18081/error-based/wrong --data username=test --tables -D "sqlinject"
        //python sqlmap.py -u  http://localhost:18081/error-based/wrong --data username=test -D "sqlinject" -T "users" --dump
        log.info("{}", jdbcTemplate.queryForList("SELECT id,username FROM users WHERE username LIKE '%" + username + "%'"));
    }

    @PostMapping("right")
    public void right(@RequestParam("username") String username) {
        //curl -X POST http://localhost:18081/error-based/right?username=test
        log.info("{}", jdbcTemplate.queryForList("SELECT id,username FROM users WHERE username LIKE ?", "%" + username + "%"));
    }

}
