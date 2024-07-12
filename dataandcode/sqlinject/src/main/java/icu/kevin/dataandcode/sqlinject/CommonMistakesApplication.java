package icu.kevin.dataandcode.sqlinject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @ExceptionHandler
    public void handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        log.warn(String.format("访问 %s -> %s 出现异常！", req.getRequestURI(), method.toString()), ex);
    }
}
