package icu.kevin.datetime;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication
@Slf4j
public class CommonMistakesApplication{

    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

}
